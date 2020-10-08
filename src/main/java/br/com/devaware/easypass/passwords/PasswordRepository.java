package br.com.devaware.easypass.passwords;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PasswordRepository extends MongoRepository<Password, String> {

    Optional<Password> findByValue(String value);
}
