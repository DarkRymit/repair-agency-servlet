package com.epam.finalproject.request;


import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class CreateWalletRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateWalletRequest that = (CreateWalletRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, currency);
    }

    @Override
    public String toString() {
        return "CreateWalletRequest{" +
                "name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
