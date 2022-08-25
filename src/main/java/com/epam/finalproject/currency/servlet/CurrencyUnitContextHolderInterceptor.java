package com.epam.finalproject.currency.servlet;

import com.epam.finalproject.currency.context.CurrencyUnitContext;
import com.epam.finalproject.currency.context.CurrencyUnitContextHolder;
import com.epam.finalproject.framework.web.RequestHandler;
import com.epam.finalproject.framework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

public class CurrencyUnitContextHolderInterceptor implements HandlerInterceptor {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CurrencyUnitContextHolderInterceptor.class);
    private CurrencyUnitContextResolver currencyUnitContextResolver;

    public CurrencyUnitContextHolderInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, RequestHandler handler) {
        CurrencyUnitContext context = currencyUnitContextResolver.resolveCurrencyUnitContext(request);
        CurrencyUnitContextHolder.setCurrencyUnitContext(context);
        return true;
    }

    public CurrencyUnitContextResolver getCurrencyUnitContextResolver() {
        return currencyUnitContextResolver;
    }

    public void setCurrencyUnitContextResolver(CurrencyUnitContextResolver currencyUnitContextResolver) {
        this.currencyUnitContextResolver = currencyUnitContextResolver;
    }

}


