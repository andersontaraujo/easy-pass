package br.com.devaware.easypass.config;

import br.com.devaware.easypass.passwords.PasswordType;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter((Converter<String, PasswordType>) mappingContext -> PasswordType.get(mappingContext.getSource()));
        modelMapper.addConverter((Converter<PasswordType, String>) mappingContext -> mappingContext.getSource().name());
        return modelMapper;
    }
}
