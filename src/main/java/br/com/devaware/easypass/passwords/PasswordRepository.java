package br.com.devaware.easypass.passwords;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasswordRepository extends MongoRepository<Password, String> {

}
