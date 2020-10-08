package br.com.devaware.easypass.passwords;

import br.com.devaware.easypass.exceptions.PasswordNotFoundException;
import br.com.devaware.easypass.passwords.dtos.CreatePasswordRequestDTO;
import br.com.devaware.easypass.passwords.dtos.UpdatePasswordRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/v1/passwords")
public class PasswordsController {

    @Autowired
    private PasswordRepository repository;

    @PostMapping
    public ResponseEntity<Password> createPassword(@Valid @RequestBody CreatePasswordRequestDTO request) {
        Password savedPassword = repository.save(Password.builder().value(request.getValue()).build());
        return ResponseEntity.ok(savedPassword);
    }

    @GetMapping
    public ResponseEntity<?> findAllPasswords() {
        List<Password> passwords = repository.findAll();
        return ResponseEntity.ok(passwords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPasswordById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PasswordNotFoundException(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Password> updatePassword(@PathVariable String id, @Valid @RequestBody UpdatePasswordRequestDTO request) {
        return repository.findById(id)
                .map(p -> {
                    Password updatedPassword = repository.save(p.toBuilder().value(request.getValue()).build());
                    return ResponseEntity.ok(updatedPassword);
                })
                .orElseThrow(() -> new PasswordNotFoundException(id));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAllPasswords() {
        repository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePasswordById(@PathVariable String id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new PasswordNotFoundException(id)));
        return ResponseEntity.noContent().build();
    }
}
