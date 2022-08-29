package com.epam.finalproject.service.impl;

import com.epam.finalproject.framework.data.transaction.PlatformTransactionManager;
import com.epam.finalproject.framework.security.password.PasswordEncoder;
import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.repository.PasswordResetTokenRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.request.NewPasswordRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetTokenServiceImplTest {
    PasswordResetToken passwordResetToken;

    User user;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    PlatformTransactionManager transactionManager;


    PasswordResetTokenServiceImpl passwordResetTokenService;

    @BeforeEach
    void setMockOutput() {
        passwordResetTokenService = new PasswordResetTokenServiceImpl(1440, passwordResetTokenRepository, userRepository,
                passwordEncoder, transactionManager);
        user = User.builder()
                .id(404L)
                .username("NotDBStriker")
                .email("notdbstrike@gmail.com")
                .firstName("NotDB")
                .password("secretPassword12")
                .lastName("Striker")
                .phone("+380 63 108 7168")
                .roles(new HashSet<>(Set.of(new Role(RoleEnum.CUSTOMER))))
                .build();
        passwordResetToken = PasswordResetToken.builder()
                .id(2L)
                .token("token")
                .user(user)
                .expiryDate(Instant.now().plusSeconds(10*60))
                .build();
    }

    @Test
    void createTokenForUser() {
        PasswordResetToken passwordResetToken = passwordResetTokenService.createTokenForUser(user);
        assertNotNull(passwordResetToken.getExpiryDate());
        assertNotNull(passwordResetToken.getToken());
        assertEquals(passwordResetToken.getUser(),user);
        assertTrue(passwordResetToken.getExpiryDate().isAfter(Instant.now()));
    }

    @Test
    void findByTokenShouldReturnCorrectObjectWhenByExistToken() {
        when(passwordResetTokenRepository.findByToken("token")).thenReturn(Optional.of(passwordResetToken));
        PasswordResetToken foundToken = passwordResetTokenService.findByToken(passwordResetToken.getToken()).orElseThrow();
        assertEquals(foundToken, passwordResetToken);
        assertEquals("token", foundToken.getToken());
    }

    @Test
    void isExpiredShouldReturnFalseWhenTimeNow() {
        assertFalse(passwordResetTokenService.isExpired(passwordResetToken));
    }

    @Test
    void isExpiredShouldReturnFalseWhenTimeIsExpiryDate() {
        assertFalse(passwordResetTokenService.isExpired(passwordResetToken, () -> passwordResetToken.getExpiryDate()));
    }

    @Test
    void isExpiredShouldReturnTrueWhenTokenExpired() {
        assertTrue(passwordResetTokenService.isExpired(passwordResetToken,
                () -> passwordResetToken.getExpiryDate().plusSeconds(60)));
    }

    @Test
    void newPasswordShouldChangePasswordWhenRequestCorrect() {
        when(passwordEncoder.encode("password")).thenReturn("secret" + "password");
        passwordResetTokenService.newPassword(passwordResetToken,new NewPasswordRequest("password"));
        assertEquals("secretpassword",user.getPassword());
        verify(userRepository, times(1)).save(user);
        verify(passwordResetTokenRepository, times(1)).delete(passwordResetToken);
    }
}