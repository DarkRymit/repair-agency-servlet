package com.epam.finalproject.dto;

import com.epam.finalproject.model.entity.AppCurrency;

import java.math.BigDecimal;
import java.util.Objects;

public class ReceiptItemDTO {

    private RepairWorkDTO repairWork;

    private BigDecimal priceAmount;

    private AppCurrency priceCurrency;

    public ReceiptItemDTO(RepairWorkDTO repairWork, BigDecimal priceAmount, AppCurrency priceCurrency) {
        this.repairWork = repairWork;
        this.priceAmount = priceAmount;
        this.priceCurrency = priceCurrency;
    }

    public ReceiptItemDTO() {
    }

    public RepairWorkDTO getRepairWork() {
        return this.repairWork;
    }

    public BigDecimal getPriceAmount() {
        return this.priceAmount;
    }

    public AppCurrency getPriceCurrency() {
        return this.priceCurrency;
    }

    public void setRepairWork(RepairWorkDTO repairWork) {
        this.repairWork = repairWork;
    }

    public void setPriceAmount(BigDecimal priceAmount) {
        this.priceAmount = priceAmount;
    }

    public void setPriceCurrency(AppCurrency priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptItemDTO that = (ReceiptItemDTO) o;
        return Objects.equals(repairWork, that.repairWork) && Objects.equals(priceAmount,
                that.priceAmount) && Objects.equals(priceCurrency, that.priceCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repairWork, priceAmount, priceCurrency);
    }

    @Override
    public String toString() {
        return "ReceiptItemDTO{" +
                "repairWork=" + repairWork +
                ", priceAmount=" + priceAmount +
                ", priceCurrency=" + priceCurrency +
                '}';
    }
}
