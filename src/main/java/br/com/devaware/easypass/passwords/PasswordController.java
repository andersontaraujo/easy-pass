package br.com.devaware.easypass.passwords;

import br.com.devaware.easypass.passwords.dtos.request.PasswordPartialDTO;
import br.com.devaware.easypass.passwords.dtos.response.PasswordDTO;
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

    private PasswordService passwordService;

    private PasswordAsyncService passwordAsyncService;

    @Autowired
    public PasswordController(PasswordService passwordService, PasswordAsyncService passwordAsyncService) {
        this.passwordService = passwordService;
        this.passwordAsyncService = passwordAsyncService;
    }

    @PostMapping
    public ResponseEntity<PasswordDTO> createPassword(@Valid @RequestBody PasswordPartialDTO request) {
        PasswordDTO password = passwordService.createPassword(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(password.getId()).toUri();
        return ResponseEntity.created(location).body(password);
    }

    @PostMapping("/async")
    public ResponseEntity<Void> createPasswordAsync(@Valid @RequestBody PasswordPartialDTO request) {
        passwordAsyncService.createPasswordAsync(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> findAllPasswords() {
        List<PasswordDTO> passwords = passwordService.findAllPasswords();
        return ResponseEntity.ok(passwords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasswordDTO> findPasswordById(@PathVariable String id) {
        PasswordDTO password = passwordService.findPasswordById(id);
        return ResponseEntity.ok(password);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PasswordDTO> updatePassword(@PathVariable String id, @Valid @RequestBody PasswordPartialDTO request) {
        PasswordDTO password = passwordService.updatePassword(id, request);
        return ResponseEntity.ok(password);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAllPasswords() {
        passwordService.removeAllPasswords();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePasswordById(@PathVariable String id) {
        passwordService.removePasswordById(id);
        return ResponseEntity.noContent().build();
    }
}
