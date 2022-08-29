package com.epam.finalproject.service.impl;

import com.epam.finalproject.framework.data.transaction.PlatformTransactionManager;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.repository.VerificationTokenRepository;
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
class VerificationTokenServiceImplTest {

    VerificationToken verificationToken;

    User user;

    Role role;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    VerificationTokenRepository verificationTokenRepository;

    @Mock
    PlatformTransactionManager transactionManager;


    VerificationTokenServiceImpl verificationTokenService;

    @BeforeEach
    void setMockOutput() {
        verificationTokenService = new VerificationTokenServiceImpl(1440, verificationTokenRepository, userRepository,
                roleRepository, transactionManager);
        role = new Role(RoleEnum.UNVERIFIED);
        user = User.builder()
                .id(404L)
                .username("NotDBStriker")
                .email("notdbstrike@gmail.com")
                .firstName("NotDB")
                .password("secretPassword")
                .lastName("Striker")
                .phone("+380 63 108 7168")
                .roles(new HashSet<>(Set.of(new Role[]{role})))
                .build();
        verificationToken = VerificationToken.builder()
                .id(2L)
                .token("token")
                .user(user)
                .expiryDate(Instant.now().plusSeconds(10 * 60))
                .build();
    }

    @Test
    void createTokenForUser() {
        VerificationToken verificationToken = verificationTokenService.createTokenForUser(user);
        assertNotNull(verificationToken.getExpiryDate());
        assertNotNull(verificationToken.getToken());
        assertEquals(verificationToken.getUser(), user);
        assertTrue(verificationToken.getExpiryDate().isAfter(Instant.now()));

    }

    @Test
    void findByTokenShouldReturnCorrectObjectWhenByExistToken() {
        when(verificationTokenRepository.findByToken("token")).thenReturn(Optional.of(verificationToken));
        VerificationToken foundToken = verificationTokenService.findByToken(verificationToken.getToken()).orElseThrow();
        assertEquals(foundToken, verificationToken);
        assertEquals("token", foundToken.getToken());
    }

    @Test
    void isExpiredShouldReturnFalseWhenTimeNow() {
        assertFalse(verificationTokenService.isExpired(verificationToken));
    }

    @Test
    void isExpiredShouldReturnFalseWhenTimeIsExpiryDate() {
        assertFalse(verificationTokenService.isExpired(verificationToken, () -> verificationToken.getExpiryDate()));
    }

    @Test
    void isExpiredShouldReturnTrueWhenTokenExpired() {
        assertTrue(verificationTokenService.isExpired(verificationToken,
                () -> verificationToken.getExpiryDate().plusSeconds(60)));
    }

    @Test
    void verifyByTokenShouldVerifyWhenTokenCorrect() {
        when(roleRepository.findByName(RoleEnum.UNVERIFIED)).thenReturn(Optional.of(role));
        verificationTokenService.verifyByToken(verificationToken);
        assertFalse(user.getRoles().contains(role));
        verify(roleRepository, times(1)).deleteRoleForUser(role, user);
        verify(verificationTokenRepository, times(1)).delete(verificationToken);

    }
}