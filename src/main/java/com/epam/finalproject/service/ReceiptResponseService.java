package com.epam.finalproject.service;

import com.epam.finalproject.dto.ReceiptResponseDTO;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.model.entity.ReceiptResponse;
import com.epam.finalproject.request.ReceiptResponseCreateRequest;

import java.util.List;

public interface ReceiptResponseService {
    Page<ReceiptResponseDTO> findAll(Pageable pageable);

    ReceiptResponseDTO createNew(ReceiptResponseCreateRequest createRequest, String username);

    ReceiptResponseDTO constructDTO(ReceiptResponse receiptResponse);


    ReceiptResponseDTO findByReceiptId(Long id);
    boolean existByReceiptId(Long id);

    Page<ReceiptResponseDTO> findByCustomerUsername(String username, Pageable pageable);
    Page<ReceiptResponseDTO> findByMasterUsername(String username, Pageable pageable);
}
