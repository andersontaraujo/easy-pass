package br.com.devaware.easypass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class EasyPassApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyPassApplication.class, args);
	}

}
