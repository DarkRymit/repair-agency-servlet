package com.epam.finalproject.dto;

import com.epam.finalproject.model.entity.AppCurrency;

import java.math.BigDecimal;
import java.util.Objects;

public class WalletDTO {

    private Long id;

    private String name;

    private BigDecimal moneyAmount;

    private AppCurrency moneyCurrency;

    public WalletDTO(Long id, String name, BigDecimal moneyAmount, AppCurrency moneyCurrency) {
        this.id = id;
        this.name = name;
        this.moneyAmount = moneyAmount;
        this.moneyCurrency = moneyCurrency;
    }

    public WalletDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getMoneyAmount() {
        return this.moneyAmount;
    }

    public AppCurrency getMoneyCurrency() {
        return this.moneyCurrency;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void setMoneyCurrency(AppCurrency moneyCurrency) {
        this.moneyCurrency = moneyCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WalletDTO walletDTO = (WalletDTO) o;
        return Objects.equals(id, walletDTO.id) && Objects.equals(name,
                walletDTO.name) && Objects.equals(moneyAmount, walletDTO.moneyAmount) && Objects.equals(
                moneyCurrency, walletDTO.moneyCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, moneyAmount, moneyCurrency);
    }

    @Override
    public String toString() {
        return "WalletDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", moneyCurrency=" + moneyCurrency +
                '}';
    }
}
