package com.epam.finalproject.request.receipt.create;

import java.util.Objects;
import java.util.Set;

public class ReceiptCreateRequest {

    Long categoryId;

    String priceCurrency;

    Set<ReceiptItemCreateRequest> receiptItems;

    ReceiptDeliveryCreateRequest receiptDelivery;

    String note;

    public ReceiptCreateRequest(Long categoryId, String priceCurrency, Set<ReceiptItemCreateRequest> receiptItems,
            ReceiptDeliveryCreateRequest receiptDelivery, String note) {
        this.categoryId = categoryId;
        this.priceCurrency = priceCurrency;
        this.receiptItems = receiptItems;
        this.receiptDelivery = receiptDelivery;
        this.note = note;
    }

    public ReceiptCreateRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptCreateRequest that = (ReceiptCreateRequest) o;
        return Objects.equals(categoryId, that.categoryId) && Objects.equals(priceCurrency,
                that.priceCurrency) && Objects.equals(receiptItems,
                that.receiptItems) && Objects.equals(receiptDelivery,
                that.receiptDelivery) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, priceCurrency, receiptItems, receiptDelivery, note);
    }

    @Override
    public String toString() {
        return "ReceiptCreateRequest{" +
                "categoryId=" + categoryId +
                ", priceCurrency='" + priceCurrency + '\'' +
                ", receiptItems=" + receiptItems +
                ", receiptDelivery=" + receiptDelivery +
                ", note='" + note + '\'' +
                '}';
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public String getPriceCurrency() {
        return this.priceCurrency;
    }

    public Set<ReceiptItemCreateRequest> getReceiptItems() {
        return this.receiptItems;
    }

    public ReceiptDeliveryCreateRequest getReceiptDelivery() {
        return this.receiptDelivery;
    }

    public String getNote() {
        return this.note;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public void setReceiptItems(Set<ReceiptItemCreateRequest> receiptItems) {
        this.receiptItems = receiptItems;
    }

    public void setReceiptDelivery(ReceiptDeliveryCreateRequest receiptDelivery) {
        this.receiptDelivery = receiptDelivery;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
