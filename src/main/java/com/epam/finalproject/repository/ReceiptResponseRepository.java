package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.ReceiptResponse;

import java.util.List;
import java.util.Optional;

public interface ReceiptResponseRepository extends PagingAndSortingRepository<ReceiptResponse, Long> {

    Page<ReceiptResponse> findAllFetchReceiptByReceipt_User_Username(String username, Pageable pageable);

    Page<ReceiptResponse> findAllFetchReceiptByReceipt_Master_Username(String username, Pageable pageable);

    Optional<ReceiptResponse> findByReceipt_Id(Long receiptId);
    boolean existsByReceipt_Id(Long id);

}
