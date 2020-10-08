package br.com.devaware.easypass.passwords;

import br.com.devaware.easypass.exceptions.ResourceNotFoundException;
import br.com.devaware.easypass.passwords.dtos.CreatePasswordRequestDTO;
import br.com.devaware.easypass.passwords.dtos.UpdatePasswordRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class PasswordService {

    @Autowired
    private PasswordRepository repository;

    public Password createPassword(CreatePasswordRequestDTO request) {
        return repository.save(Password.builder().value(request.getValue()).build());
    }

    public List<Password> findAllPasswords() {
        return repository.findAll();
    }

    public Password findPasswordById(String id) {
        return repository.findById(id).orElseThrow(throwException(id));
    }

    public Password updatePassword(String id, UpdatePasswordRequestDTO request) {
        return repository.findById(id)
                .map(password -> repository.save(password.toBuilder().value(request.getValue()).build()))
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

}
