package br.com.devaware.easypass.passwords;

import br.com.devaware.easypass.exceptions.ResourceNotFoundException;
import br.com.devaware.easypass.messaging.SimpleProducer;
import br.com.devaware.easypass.passwords.dtos.messaging.PasswordMessageDTO;
import br.com.devaware.easypass.passwords.dtos.messaging.PasswordMessageErrorDTO;
import br.com.devaware.easypass.passwords.dtos.request.PasswordPartialDTO;
import br.com.devaware.easypass.passwords.dtos.response.PasswordDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class PasswordService {

    private PasswordRepository repository;

    private ModelMapper mapper;

    private SimpleProducer simpleProducer;



    @Autowired
    public PasswordService(PasswordRepository repository, ModelMapper mapper, SimpleProducer simpleProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.simpleProducer = simpleProducer;
    }

    public PasswordDTO createPassword(PasswordPartialDTO request) {
        Password savedPassword = repository.save(mapper.map(request, Password.class));
        return mapper.map(savedPassword, PasswordDTO.class);
    }

    public PasswordMessageDTO createPasswordAsync(PasswordPartialDTO request) {

        PasswordMessageDTO message = null;

        try {

            Password savedPassword = repository.save(mapper.map(request, Password.class));

            PasswordDTO password = mapper.map(savedPassword, PasswordDTO.class);

            message = PasswordMessageDTO.builder()
                    .password(password)
                    .isComplete(true)
                    .build();

        } catch (Exception e) {

            message = PasswordMessageDTO.builder()
                    .isComplete(true)
                    .errors(composeErrors(e))
                    .build();

        }

        return message;
    }


    private List<PasswordMessageErrorDTO> composeErrors(Exception e) {
        return Collections.singletonList(PasswordMessageErrorDTO.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .details(Collections.singletonList(e.getMessage()))
                .build());
    }


    public List<PasswordDTO> findAllPasswords() {
        return repository.findAll().stream()
                .map(password -> mapper.map(password, PasswordDTO.class))
                .collect(Collectors.toList());
    }

    public PasswordDTO findPasswordById(String id) {
        return repository.findById(id)
                .map(password -> mapper.map(password, PasswordDTO.class))
                .orElseThrow(processError(id));
    }

    public PasswordDTO updatePassword(String id, PasswordPartialDTO request) {
        return repository.findById(id)
                .map(password -> {
                    Password updatedPassword = repository.save(mergePassword().apply(password, mapper.map(request, Password.class)));
                    return mapper.map(updatedPassword, PasswordDTO.class);
                })
                .orElseThrow(processError(id));
    }

    public void removeAllPasswords() {
        repository.deleteAll();
    }

    public void removePasswordById(String id) {
        repository.delete(repository.findById(id).orElseThrow(processError(id)));
    }

    private Supplier<RuntimeException> processError(String id) {
        return () -> new ResourceNotFoundException(id);
    }

    private BinaryOperator<Password> mergePassword() {
        return (basePassword, newPassword) -> Password.builder()
                .id(basePassword.getId())
                .value(newPassword.getValue())
                .type(newPassword.getType())
                .createdDate(basePassword.getCreatedDate())
                .modifiedDate(basePassword.getModifiedDate())
                .build();
    }

}
