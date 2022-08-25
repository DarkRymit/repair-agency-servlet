package com.epam.finalproject.dto;

import com.epam.finalproject.model.entity.AppCurrency;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class RepairWorkDTO {

    private Long id;

    private String keyName;

    private Set<RepairWorkStatusDTO> statuses;

    private String name;

    private BigDecimal lowerBorder;

    private BigDecimal upperBorder;

    private AppCurrency currency;

    public RepairWorkDTO(Long id, String keyName, Set<RepairWorkStatusDTO> statuses, String name,
            BigDecimal lowerBorder, BigDecimal upperBorder, AppCurrency currency) {
        this.id = id;
        this.keyName = keyName;
        this.statuses = statuses;
        this.name = name;
        this.lowerBorder = lowerBorder;
        this.upperBorder = upperBorder;
        this.currency = currency;
    }

    public RepairWorkDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public Set<RepairWorkStatusDTO> getStatuses() {
        return this.statuses;
    }

    public String getName() {
        return this.name;
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

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public void setStatuses(Set<RepairWorkStatusDTO> statuses) {
        this.statuses = statuses;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepairWorkDTO that = (RepairWorkDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(keyName,
                that.keyName) && Objects.equals(statuses, that.statuses) && Objects.equals(name,
                that.name) && Objects.equals(lowerBorder, that.lowerBorder) && Objects.equals(
                upperBorder, that.upperBorder) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyName, statuses, name, lowerBorder, upperBorder, currency);
    }

    @Override
    public String toString() {
        return "RepairWorkDTO{" +
                "id=" + id +
                ", keyName='" + keyName + '\'' +
                ", statuses=" + statuses +
                ", name='" + name + '\'' +
                ", lowerBorder=" + lowerBorder +
                ", upperBorder=" + upperBorder +
                ", currency=" + currency +
                '}';
    }
}
