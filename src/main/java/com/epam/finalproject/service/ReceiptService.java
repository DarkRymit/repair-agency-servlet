package com.epam.finalproject.service;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.request.receipt.create.ReceiptCreateRequest;
import com.epam.finalproject.request.receipt.pay.ReceiptPayRequest;
import com.epam.finalproject.request.receipt.update.ReceiptUpdateRequest;

import java.util.List;

public interface ReceiptService {

    ReceiptDTO createNew(ReceiptCreateRequest createRequest, String username);

    ReceiptDTO update(ReceiptUpdateRequest updateRequest);

    ReceiptDTO updateStatus(Long receiptId,Long statusId, String username);

    ReceiptDTO constructDTO(Receipt receipt);

    ReceiptDTO findById(Long id);

    ReceiptDTO pay(ReceiptPayRequest payRequest, String username);
}
