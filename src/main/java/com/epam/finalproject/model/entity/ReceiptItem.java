package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlReferenceId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.math.BigDecimal;
import java.util.Objects;


@SqlTable("receipt_items")
public class ReceiptItem {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlReferenceId
    @SqlColumn("receipt_id")
    Receipt receipt;

    @SqlReferenceId
    @SqlColumn("repair_work_id")
    RepairWork repairWork;

    @SqlColumn("price_amount")
    private BigDecimal priceAmount;

    public ReceiptItem(Long id, Receipt receipt, RepairWork repairWork, BigDecimal priceAmount) {
        this.id = id;
        this.receipt = receipt;
        this.repairWork = repairWork;
        this.priceAmount = priceAmount;
    }

    public ReceiptItem() {
    }

    public static ReceiptItemBuilder builder() {
        return new ReceiptItemBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Receipt getReceipt() {
        return this.receipt;
    }

    public RepairWork getRepairWork() {
        return this.repairWork;
    }

    public BigDecimal getPriceAmount() {
        return this.priceAmount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public void setRepairWork(RepairWork repairWork) {
        this.repairWork = repairWork;
    }

    public void setPriceAmount(BigDecimal priceAmount) {
        this.priceAmount = priceAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptItem that = (ReceiptItem) o;
        return Objects.equals(id, that.id) && Objects.equals(repairWork, that.repairWork) && Objects.equals(
                priceAmount, that.priceAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, repairWork, priceAmount);
    }

    @Override
    public String toString() {
        return "ReceiptItem{" +
                "id=" + id +
                ", repairWork=" + repairWork +
                ", priceAmount=" + priceAmount +
                '}';
    }

    public static class ReceiptItemBuilder {
        private Long id;
        private Receipt receipt;
        private RepairWork repairWork;
        private BigDecimal priceAmount;

        ReceiptItemBuilder() {
        }

        public ReceiptItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReceiptItemBuilder receipt(Receipt receipt) {
            this.receipt = receipt;
            return this;
        }

        public ReceiptItemBuilder repairWork(RepairWork repairWork) {
            this.repairWork = repairWork;
            return this;
        }

        public ReceiptItemBuilder priceAmount(BigDecimal priceAmount) {
            this.priceAmount = priceAmount;
            return this;
        }

        public ReceiptItem build() {
            return new ReceiptItem(id, receipt, repairWork, priceAmount);
        }

        public String toString() {
            return "ReceiptItem.ReceiptItemBuilder(id=" + this.id + ", receipt=" + this.receipt + ", repairWork=" + this.repairWork + ", priceAmount=" + this.priceAmount + ")";
        }
    }
}
