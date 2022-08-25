package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlReferenceId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.math.BigDecimal;
import java.util.Objects;


@SqlTable("repair_work_prices")
public class RepairWorkPrice {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlReferenceId
    @SqlColumn("work_id")
    private RepairWork work;

    @SqlColumn("lower_border")
    private BigDecimal lowerBorder;

    @SqlColumn("upper_border")
    private BigDecimal upperBorder;

    @SqlReferenceId
    @SqlColumn("currency_id")
    private AppCurrency currency;

    public RepairWorkPrice(Long id, RepairWork work, BigDecimal lowerBorder, BigDecimal upperBorder,
            AppCurrency currency) {
        this.id = id;
        this.work = work;
        this.lowerBorder = lowerBorder;
        this.upperBorder = upperBorder;
        this.currency = currency;
    }

    public RepairWorkPrice() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepairWorkPrice price = (RepairWorkPrice) o;
        return Objects.equals(id, price.id) && Objects.equals(lowerBorder, price.lowerBorder) && Objects.equals(
                upperBorder, price.upperBorder) && Objects.equals(currency, price.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lowerBorder, upperBorder, currency);
    }

    @Override
    public String toString() {
        return "RepairWorkPrice{" +
                "id=" + id +
                ", lowerBorder=" + lowerBorder +
                ", upperBorder=" + upperBorder +
                ", currency=" + currency +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public RepairWork getWork() {
        return this.work;
    }

    public BigDecimal getLowerBorder() {
        return this.lowerBorder;
    }

    public BigDecimal getUpperBorder() {
        return this.upperBorder;
    }

    public AppCurrency getCurrency() {
        return this.currency;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWork(RepairWork work) {
        this.work = work;
    }

    public void setLowerBorder(BigDecimal lowerBorder) {
        this.lowerBorder = lowerBorder;
    }

    public void setUpperBorder(BigDecimal upperBorder) {
        this.upperBorder = upperBorder;
    }

    public void setCurrency(AppCurrency currency) {
        this.currency = currency;
    }
}
