package br.com.devaware.easypass.passwords;

import br.com.devaware.easypass.exceptions.ResourceNotFoundException;
import br.com.devaware.easypass.passwords.dtos.request.CreatePasswordRequestDTO;
import br.com.devaware.easypass.passwords.dtos.request.UpdatePasswordRequestDTO;
import br.com.devaware.easypass.passwords.dtos.response.PasswordDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class PasswordService {

    @Autowired
    private PasswordRepository repository;

    @Autowired
    private ModelMapper mapper;

    public PasswordDTO createPassword(CreatePasswordRequestDTO request) {
        Password savedPassword = repository.save(mapper.map(request, Password.class));
        return mapper.map(savedPassword, PasswordDTO.class);
    }

    public List<PasswordDTO> findAllPasswords() {
        return repository.findAll().stream()
                .map(password -> mapper.map(password, PasswordDTO.class))
                .collect(Collectors.toList());
    }

    public PasswordDTO findPasswordById(String id) {
        return repository.findById(id)
                .map(password -> mapper.map(password, PasswordDTO.class))
                .orElseThrow(throwException(id));
    }

    public PasswordDTO updatePassword(String id, UpdatePasswordRequestDTO request) {
        return repository.findById(id)
                .map(handlePasswordUpdating(request))
                .orElseThrow(throwException(id));
    }

    public void removeAllPasswords() {
        repository.deleteAll();
    }

    public void removePasswordById(String id) {
        repository.delete(repository.findById(id).orElseThrow(throwException(id)));
    }

    private Supplier<RuntimeException> throwException(String id) {
        return () -> new ResourceNotFoundException(id);
    }

    private Function<Password, PasswordDTO> handlePasswordUpdating(UpdatePasswordRequestDTO request) {
        return password -> {
            Password updatedPassword = repository.save(mergePassword().apply(password, mapper.map(request, Password.class)));
            return mapper.map(updatedPassword, PasswordDTO.class);
        };
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
