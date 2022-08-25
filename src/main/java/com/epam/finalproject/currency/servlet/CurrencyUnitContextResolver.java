package com.epam.finalproject.currency.servlet;

import com.epam.finalproject.currency.context.CurrencyUnitContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CurrencyUnitContextResolver extends CurrencyUnitResolver {
    CurrencyUnitContext resolveCurrencyUnitContext(HttpServletRequest request);
    void setCurrencyUnitContext(HttpServletRequest request, HttpServletResponse response, CurrencyUnitContext context);
}
