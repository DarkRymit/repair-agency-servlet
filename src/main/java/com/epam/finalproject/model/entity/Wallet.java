package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlReferenceId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


@SqlTable("wallets")
public class Wallet implements Serializable {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlReferenceId(isEntity = false)
    @SqlColumn("user_id")
    private Long userId;

    @SqlColumn("name")
    private String name;

    @SqlColumn("money_amount")
    private BigDecimal moneyAmount;

    private AppCurrency moneyCurrency;

    public Wallet(Long id, Long userId, String name, BigDecimal moneyAmount, AppCurrency moneyCurrency) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.moneyAmount = moneyAmount;
        this.moneyCurrency = moneyCurrency;
    }

    public Wallet() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wallet wallet = (Wallet) o;
        return Objects.equals(id, wallet.id) && Objects.equals(userId,
                wallet.userId) && Objects.equals(name, wallet.name) && Objects.equals(moneyAmount,
                wallet.moneyAmount) && Objects.equals(moneyCurrency, wallet.moneyCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name, moneyAmount, moneyCurrency);
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", moneyCurrency=" + moneyCurrency +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return this.userId;
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

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
