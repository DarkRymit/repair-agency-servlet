package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.AppCurrency;

import java.util.Optional;

public interface AppCurrencyRepository extends PagingAndSortingRepository<AppCurrency, Long> {
    Optional<AppCurrency> findByCode(String code);

}
