package com.epam.finalproject.model.search;

import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@AllArgsConstructor
@Builder
public class ReceiptWithCustomerSearch {
    PageRequest pageRequest;
    Set<ReceiptStatusEnum> receiptStatuses;
    String masterUsername;
    User customer;
}
