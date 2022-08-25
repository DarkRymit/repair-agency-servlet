package com.epam.finalproject.currency.servlet;

import com.epam.finalproject.currency.context.CurrencyUnitContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.money.CurrencyUnit;

public class SessionCurrencyUnitResolver extends AbstractCurrencyUnitContextResolver implements CurrencyUnitContextResolver {

    public static final String CURRENCY_UNIT_SESSION_ATTRIBUTE_NAME = SessionCurrencyUnitResolver.class.getName() + ".CURRENCY";
    private String currencyUnitAttributeName = CURRENCY_UNIT_SESSION_ATTRIBUTE_NAME;

    @Override
    public CurrencyUnit resolveCurrencyUnit(HttpServletRequest request) {
        CurrencyUnit currencyUnit = (CurrencyUnit)request.getSession().getAttribute(currencyUnitAttributeName);
        if (currencyUnit == null) {
            currencyUnit = getDefaultCurrencyUnit();
        }

        return currencyUnit;
    }

    @Override
    public CurrencyUnitContext resolveCurrencyUnitContext(HttpServletRequest request) {
        return () -> resolveCurrencyUnit(request);
    }

    @Override
    public void setCurrencyUnitContext(HttpServletRequest request, HttpServletResponse response, CurrencyUnitContext context) {
        request.getSession().setAttribute(currencyUnitAttributeName, context.getCurrencyUnit());
    }

    public String getCurrencyUnitAttributeName() {
        return currencyUnitAttributeName;
    }

    public void setCurrencyUnitAttributeName(String currencyUnitAttributeName) {
        this.currencyUnitAttributeName = currencyUnitAttributeName;
    }
}

