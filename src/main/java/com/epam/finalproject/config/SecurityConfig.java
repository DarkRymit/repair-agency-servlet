package com.epam.finalproject.config;

import com.epam.finalproject.framework.beans.annotation.Bean;
import com.epam.finalproject.framework.beans.annotation.Configuration;
import com.epam.finalproject.framework.security.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    public static final String ENCODED = "Encoded";

    @Bean(clazz = PasswordEncoder.class)
    PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return ENCODED + rawPassword;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return true;
            }
        };
    }

}
