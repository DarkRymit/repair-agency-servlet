package com.epam.finalproject.service.impl;

import com.epam.finalproject.framework.beans.annotation.Value;
import com.epam.finalproject.framework.data.transaction.PlatformTransactionManager;
import com.epam.finalproject.framework.data.transaction.TransactionStatus;
import com.epam.finalproject.framework.security.password.PasswordEncoder;
import com.epam.finalproject.framework.web.annotation.Service;
import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.repository.PasswordResetTokenRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.request.NewPasswordRequest;
import com.epam.finalproject.service.PasswordResetTokenService;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PasswordResetTokenServiceImpl.class);
    private final Integer expiration;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final PlatformTransactionManager transactionManager;

    public PasswordResetTokenServiceImpl(@Value("1400") Integer expiration, PasswordResetTokenRepository passwordResetTokenRepository, UserRepository userRepository, PasswordEncoder passwordEncoder,
            PlatformTransactionManager transactionManager) {
        this.expiration = expiration;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.transactionManager = transactionManager;
    }

    @Override
    public PasswordResetToken createTokenForUser(User user) {
                String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(calculateExpiryDate())
                .build();
        passwordResetTokenRepository.save(passwordResetToken);
        log.info("Create token :" + passwordResetToken);
        return passwordResetToken;
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public boolean isExpired(PasswordResetToken passwordResetToken) {
        return isExpired(passwordResetToken, Instant::now);
    }

    @Override
    public boolean isExpired(PasswordResetToken passwordResetToken, Supplier<Instant> dateSupplier) {
        return passwordResetToken.getExpiryDate().isBefore(dateSupplier.get());
    }

    @Override
    public void newPassword(PasswordResetToken token, NewPasswordRequest newPasswordRequest) {
        TransactionStatus ts = transactionManager.getTransaction();
        try {

            User user = token.getUser();
            user.setPassword(passwordEncoder.encode(newPasswordRequest.getPassword()));
            userRepository.save(user);
            passwordResetTokenRepository.delete(token);

        } catch (Exception e) {
            transactionManager.rollback(ts);
            throw new RuntimeException(e);
        }
        transactionManager.commit(ts);

    }
    private Instant calculateExpiryDate() {
        return Instant.now().plus(Duration.ofMinutes(expiration));
    }
}
