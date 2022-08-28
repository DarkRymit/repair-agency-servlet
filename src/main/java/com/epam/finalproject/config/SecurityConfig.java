package com.epam.finalproject.config;

import com.epam.finalproject.framework.beans.annotation.Bean;
import com.epam.finalproject.framework.beans.annotation.Configuration;
import com.epam.finalproject.framework.security.password.PasswordEncoder;
import com.epam.finalproject.framework.security.password.Pbkdf2PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean(clazz = PasswordEncoder.class)
    PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

}
