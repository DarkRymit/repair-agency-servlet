package com.epam.finalproject.repository;


import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.VerificationToken;

import java.util.Date;
import java.util.Optional;


public interface VerificationTokenRepository extends PagingAndSortingRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    void deleteAllExpiredSince(Date now);
}
