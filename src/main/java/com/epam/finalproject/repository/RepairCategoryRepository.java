package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.RepairCategory;
import com.epam.finalproject.model.entity.RepairCategoryLocalPart;

import java.util.Optional;


public interface RepairCategoryRepository extends PagingAndSortingRepository<RepairCategory, Long> {
    Optional<RepairCategory> findByKeyName(String key);

    Optional<RepairCategoryLocalPart> findFirstLocalPartByCategory_IdAndLanguage_Lang(Long categoryId,String lang);
}
