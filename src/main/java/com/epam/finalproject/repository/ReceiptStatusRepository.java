package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.ReceiptStatus;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;

import java.util.Optional;


public interface ReceiptStatusRepository extends PagingAndSortingRepository<ReceiptStatus, Long> {
    Optional<ReceiptStatus> findByName(ReceiptStatusEnum name);

}
