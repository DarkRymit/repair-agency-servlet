package com.epam.finalproject.request;


import java.math.BigDecimal;
import java.util.Objects;

public class AddMoneyRequest {

    private Long id;

    private BigDecimal moneyToAdd;

    public AddMoneyRequest() {
    }

    public AddMoneyRequest(Long id, BigDecimal moneyToAdd) {
        this.id = id;
        this.moneyToAdd = moneyToAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddMoneyRequest that = (AddMoneyRequest) o;
        return Objects.equals(id, that.id) && Objects.equals(moneyToAdd, that.moneyToAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, moneyToAdd);
    }

    @Override
    public String toString() {
        return "AddMoneyRequest{" +
                "id=" + id +
                ", moneyToAdd=" + moneyToAdd +
                '}';
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getMoneyToAdd() {
        return moneyToAdd;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMoneyToAdd(BigDecimal moneyToAdd) {
        this.moneyToAdd = moneyToAdd;
    }
}
