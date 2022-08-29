package com.epam.finalproject.service.impl;

import com.epam.finalproject.framework.beans.annotation.Value;
import com.epam.finalproject.framework.data.transaction.PlatformTransactionManager;
import com.epam.finalproject.framework.data.transaction.TransactionStatus;
import com.epam.finalproject.framework.web.annotation.Service;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.repository.VerificationTokenRepository;
import com.epam.finalproject.service.VerificationTokenService;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(VerificationTokenServiceImpl.class);
    private final Integer expiration;

    private final VerificationTokenRepository verificationTokenRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PlatformTransactionManager transactionManager;

    public VerificationTokenServiceImpl(@Value("1400") Integer expiration,
            VerificationTokenRepository verificationTokenRepository, UserRepository userRepository,
            RoleRepository roleRepository,
            PlatformTransactionManager transactionManager) {
        this.expiration = expiration;
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public VerificationToken createTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .expiryDate(calculateExpiryDate())
                .build();
        verificationTokenRepository.save(verificationToken);
        log.info("Create token :" + verificationToken);
        return verificationToken;
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public boolean isExpired(VerificationToken verificationToken) {
        return isExpired(verificationToken, Instant::now);
    }

    @Override
    public boolean isExpired(VerificationToken verificationToken, Supplier<Instant> dateSupplier) {
        return verificationToken.getExpiryDate().isBefore(dateSupplier.get());
    }

    @Override
    public void verifyByToken(VerificationToken token) {
        TransactionStatus ts = transactionManager.getTransaction();
        try {
            Role role = roleRepository.findByName(RoleEnum.UNVERIFIED).orElseThrow();
            User user = token.getUser();
            user.deleteRole(role);
            roleRepository.deleteRoleForUser(role, user);
            userRepository.save(user);
            verificationTokenRepository.delete(token);
        } catch (Exception e) {
            transactionManager.rollback(ts);
            throw new RuntimeException(e);
        }
    }

    private Instant calculateExpiryDate() {
        return Instant.now().plus(Duration.ofMinutes(expiration));
    }
}
