package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.AppLocale;

import java.util.Optional;


public interface AppLocaleRepository extends PagingAndSortingRepository<AppLocale, Long> {
    Optional<AppLocale> findByLang(String lang);

}
