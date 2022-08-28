package com.epam.finalproject.framework.validation;

import com.epam.finalproject.framework.beans.annotation.Bean;
import com.epam.finalproject.framework.beans.annotation.Configuration;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;

@Configuration
public class ValidationConfig {

    @Bean
    public ValidatorFactory validatorFactory(){
        return Validation.buildDefaultValidatorFactory();
    }

    @Bean
    public ExecutableValidator executableValidator(ValidatorFactory factory){
        return factory.getValidator().forExecutables();
    }

    @Bean
    public Validator validator(ValidatorFactory factory){
        return factory.getValidator();
    }

}
