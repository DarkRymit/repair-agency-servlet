package com.epam.finalproject.currency.servlet;

import com.epam.finalproject.framework.web.RequestHandler;
import com.epam.finalproject.framework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

public class CurrencyUnitChangeInterceptor implements HandlerInterceptor {
    public static final String DEFAULT_PARAM_NAME = "currency";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CurrencyUnitChangeInterceptor.class);
    private String paramName = DEFAULT_PARAM_NAME;

    private CurrencyUnitResolver currencyUnitResolver;

    private boolean ignoreInvalidCurrency = false;

    public CurrencyUnitChangeInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, RequestHandler handler) {
        String newCurrencyUnit = request.getParameter(paramName);
        if (newCurrencyUnit != null) {
            try {
                currencyUnitResolver.setCurrencyUnit(request, response, parseCurrencyUnitValue(newCurrencyUnit));
            } catch (IllegalArgumentException e) {
                if (!isIgnoreInvalidCurrency()) {
                    throw e;
                }
                log.debug("Ignoring invalid currency value [" + newCurrencyUnit + "]: " + e.getMessage());
            }
        }
        return true;
    }

    protected CurrencyUnit parseCurrencyUnitValue(String currencyValue) {
        return Monetary.getCurrency(currencyValue);
    }

    public CurrencyUnitResolver getCurrencyUnitResolver() {
        return currencyUnitResolver;
    }

    public void setCurrencyUnitResolver(CurrencyUnitResolver currencyUnitResolver) {
        this.currencyUnitResolver = currencyUnitResolver;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public boolean isIgnoreInvalidCurrency() {
        return ignoreInvalidCurrency;
    }

    public void setIgnoreInvalidCurrency(boolean ignoreInvalidCurrency) {
        this.ignoreInvalidCurrency = ignoreInvalidCurrency;
    }
}


