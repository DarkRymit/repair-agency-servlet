package com.epam.finalproject.config;

import com.epam.finalproject.framework.beans.annotation.Bean;
import com.epam.finalproject.framework.beans.annotation.Configuration;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    Properties mailProperties() throws IOException {
        try (InputStream input = EmailConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        }
    }

    @Bean
    Session messageSession(Properties mailProperties) {
        return Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailProperties.getProperty("mail.smtp.auth.username"),
                        mailProperties.getProperty("mail.smtp.auth.password"));
            }
        });
    }

}
