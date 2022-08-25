package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.PasswordResetToken;

import java.util.Date;
import java.util.Optional;


public interface PasswordResetTokenRepository extends PagingAndSortingRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteAllExpiredSince(Date now);
}
