package br.com.devaware.easypass.passwords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/passwords")
public class PasswordsController {

    @Autowired
    private PasswordRepository repository;

    @PostMapping
    public ResponseEntity<Password> createPassword(@RequestBody Password password) {
        Password savedPassword = repository.save(password);
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
                .orElseThrow(() -> new RuntimeException(String.format("Password with id [%s] not found.", id)));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAllPasswords() {
        repository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePasswordById(@PathVariable String id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Password with id [%s] not found.", id))));
        return ResponseEntity.noContent().build();
    }
}
