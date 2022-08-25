package com.epam.finalproject.currency.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.money.CurrencyUnit;

public interface CurrencyUnitResolver {
    CurrencyUnit resolveCurrencyUnit(HttpServletRequest request);

    void setCurrencyUnit(HttpServletRequest request, HttpServletResponse response, CurrencyUnit currencyUnit);
}
