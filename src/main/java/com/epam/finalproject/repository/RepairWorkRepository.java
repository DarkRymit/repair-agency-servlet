package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.RepairWork;
import com.epam.finalproject.model.entity.RepairWorkLocalPart;
import com.epam.finalproject.model.entity.RepairWorkPrice;

import java.util.List;
import java.util.Optional;


public interface RepairWorkRepository extends PagingAndSortingRepository<RepairWork, Long> {
    List<RepairWork> findByKeyName(String key);
    List<RepairWork> findByCategoryKeyName(String key);
    Optional<RepairWork> findByKeyNameAndCategory_KeyName(String workKey, String categoryKey);
    Optional<RepairWorkLocalPart> findLocalByWork_IdAndLanguage_Lang(Long workId, String lang);
    Optional<RepairWorkPrice> findPriceByWork_IdAndCurrency_Code(Long workId, String code);

}
