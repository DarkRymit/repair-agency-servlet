package com.epam.finalproject.request.receipt.update;

import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.Set;

public class ReceiptUpdateRequest {

    @JsonIgnore
    Long id;

    ReceiptStatusEnum receiptStatus;

    String masterUsername;

    Set<ReceiptItemUpdateRequest> receiptItems;

    ReceiptDeliveryUpdateRequest receiptDelivery;

    String priceCurrency;

    String note;

    public ReceiptUpdateRequest(Long id, ReceiptStatusEnum receiptStatus, String masterUsername,
            Set<ReceiptItemUpdateRequest> receiptItems, ReceiptDeliveryUpdateRequest receiptDelivery,
            String priceCurrency, String note) {
        this.id = id;
        this.receiptStatus = receiptStatus;
        this.masterUsername = masterUsername;
        this.receiptItems = receiptItems;
        this.receiptDelivery = receiptDelivery;
        this.priceCurrency = priceCurrency;
        this.note = note;
    }

    public ReceiptUpdateRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptUpdateRequest that = (ReceiptUpdateRequest) o;
        return Objects.equals(id, that.id) && receiptStatus == that.receiptStatus && Objects.equals(
                masterUsername, that.masterUsername) && Objects.equals(receiptItems,
                that.receiptItems) && Objects.equals(receiptDelivery,
                that.receiptDelivery) && Objects.equals(priceCurrency,
                that.priceCurrency) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receiptStatus, masterUsername, receiptItems, receiptDelivery, priceCurrency, note);
    }

    @Override
    public String toString() {
        return "ReceiptUpdateRequest{" +
                "id=" + id +
                ", receiptStatus=" + receiptStatus +
                ", masterUsername='" + masterUsername + '\'' +
                ", receiptItems=" + receiptItems +
                ", receiptDelivery=" + receiptDelivery +
                ", priceCurrency='" + priceCurrency + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public ReceiptStatusEnum getReceiptStatus() {
        return this.receiptStatus;
    }

    public String getMasterUsername() {
        return this.masterUsername;
    }

    public Set<ReceiptItemUpdateRequest> getReceiptItems() {
        return this.receiptItems;
    }

    public ReceiptDeliveryUpdateRequest getReceiptDelivery() {
        return this.receiptDelivery;
    }

    public String getPriceCurrency() {
        return this.priceCurrency;
    }

    public String getNote() {
        return this.note;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    public void setReceiptStatus(ReceiptStatusEnum receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public void setMasterUsername(String masterUsername) {
        this.masterUsername = masterUsername;
    }

    public void setReceiptItems(Set<ReceiptItemUpdateRequest> receiptItems) {
        this.receiptItems = receiptItems;
    }

    public void setReceiptDelivery(ReceiptDeliveryUpdateRequest receiptDelivery) {
        this.receiptDelivery = receiptDelivery;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
