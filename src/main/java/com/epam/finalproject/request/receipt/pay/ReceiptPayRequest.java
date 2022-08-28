package com.epam.finalproject.request.receipt.pay;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class ReceiptPayRequest {

    @JsonIgnore
    Long id;

    @NotNull
    Long walletId;

    public ReceiptPayRequest(Long id, Long walletId) {
        this.id = id;
        this.walletId = walletId;
    }

    public ReceiptPayRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptPayRequest that = (ReceiptPayRequest) o;
        return Objects.equals(id, that.id) && Objects.equals(walletId, that.walletId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, walletId);
    }

    @Override
    public String toString() {
        return "ReceiptPayRequest{" +
                "id=" + id +
                ", walletId=" + walletId +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public Long getWalletId() {
        return this.walletId;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }
}
