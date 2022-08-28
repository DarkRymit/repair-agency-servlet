package com.epam.finalproject.request.receipt.update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Objects;

public class ReceiptItemUpdateRequest {
    @NotNull
    Long repairWorkID;
    @PositiveOrZero
    BigDecimal priceAmount;

    public ReceiptItemUpdateRequest(Long repairWorkID, BigDecimal priceAmount) {
        this.repairWorkID = repairWorkID;
        this.priceAmount = priceAmount;
    }

    public ReceiptItemUpdateRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptItemUpdateRequest that = (ReceiptItemUpdateRequest) o;
        return Objects.equals(repairWorkID, that.repairWorkID) && Objects.equals(priceAmount,
                that.priceAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repairWorkID, priceAmount);
    }

    @Override
    public String toString() {
        return "ReceiptItemUpdateRequest{" +
                "repairWorkID=" + repairWorkID +
                ", priceAmount=" + priceAmount +
                '}';
    }

    public Long getRepairWorkID() {
        return this.repairWorkID;
    }

    public void setRepairWorkID(Long repairWorkID) {
        this.repairWorkID = repairWorkID;
    }

    public BigDecimal getPriceAmount() {
        return this.priceAmount;
    }

    public void setPriceAmount(BigDecimal priceAmount) {
        this.priceAmount = priceAmount;
    }
}
