package com.epam.finalproject.config;

import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;

import java.util.Map;

public class SearchParameters {

    Map<String, Sort> receiptSort;

    Map<String, Sort> userSort;

    Map<String, ReceiptStatusEnum> receiptStatus;

    public SearchParameters(Map<String, Sort> receiptSort, Map<String, Sort> userSort,
            Map<String, ReceiptStatusEnum> receiptStatus) {
        this.receiptSort = receiptSort;
        this.userSort = userSort;
        this.receiptStatus = receiptStatus;
    }

    public Map<String, Sort> getReceiptSort() {
        return this.receiptSort;
    }

    public Map<String, Sort> getUserSort() {
        return this.userSort;
    }

    public Map<String, ReceiptStatusEnum> getReceiptStatus() {
        return this.receiptStatus;
    }
}
