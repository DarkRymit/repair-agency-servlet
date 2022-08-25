package com.epam.finalproject.currency.servlet;

import com.epam.finalproject.currency.context.SimpleCurrencyUnitContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.money.CurrencyUnit;

public abstract class AbstractCurrencyUnitContextResolver extends AbstractCurrencyUnitResolver implements CurrencyUnitContextResolver {


    @Override
    public CurrencyUnit resolveCurrencyUnit(HttpServletRequest request) {
        return resolveCurrencyUnitContext(request).getCurrencyUnit();
    }

    @Override
    public void setCurrencyUnit(HttpServletRequest request, HttpServletResponse response, CurrencyUnit currencyUnit) {
        setCurrencyUnitContext(request, response, currencyUnit != null ? new SimpleCurrencyUnitContext(currencyUnit) : null);
    }

}

