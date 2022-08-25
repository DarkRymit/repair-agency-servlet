package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.ReceiptDelivery;
import com.epam.finalproject.model.entity.ReceiptItem;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.search.ReceiptSearch;
import com.epam.finalproject.model.search.ReceiptWithCustomerSearch;
import com.epam.finalproject.model.search.ReceiptWithMasterSearch;

import java.time.Instant;
import java.util.List;


public interface ReceiptRepository extends PagingAndSortingRepository<Receipt, Long> {

    Page<Receipt> findAll(ReceiptWithMasterSearch receiptSearch);

    Page<Receipt> findAll(ReceiptWithCustomerSearch receiptSearch);

    Page<Receipt> findAll(ReceiptSearch receiptSearch);

    ReceiptItem saveItem(ReceiptItem entity);

    void deleteItem(ReceiptItem entity);

    ReceiptDelivery saveDelivery(ReceiptDelivery entity);

    void deleteDelivery(ReceiptDelivery entity);

}
