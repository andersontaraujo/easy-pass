package br.com.devaware.easypass.passwords;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/passwords")
public class PasswordController {

    @Autowired
    private PasswordService service;

    @PostMapping
    public ResponseEntity<Password> createPassword(@Valid @RequestBody CreatePasswordRequestDTO request) {
        Password savedPassword = service.createPassword(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPassword.getId()).toUri();
        return ResponseEntity.created(location).body(savedPassword);
    }

    @GetMapping
    public ResponseEntity<?> findAllPasswords() {
        List<Password> passwords = service.findAllPasswords();
        return ResponseEntity.ok(passwords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPasswordById(@PathVariable String id) {
        Password password = service.findPasswordById(id);
        return ResponseEntity.ok(password);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Password> updatePassword(@PathVariable String id, @Valid @RequestBody UpdatePasswordRequestDTO request) {
        Password password = service.updatePassword(id, request);
        return ResponseEntity.ok(password);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAllPasswords() {
        service.removeAllPasswords();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePasswordById(@PathVariable String id) {
        service.removePasswordById(id);
        return ResponseEntity.noContent().build();
    }
}
