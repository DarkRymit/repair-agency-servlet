package com.epam.finalproject.currency.servlet;

import com.epam.finalproject.currency.context.CurrencyUnitContext;
import com.epam.finalproject.currency.context.CurrencyUnitContextHolder;
import com.epam.finalproject.framework.web.RequestHandler;
import com.epam.finalproject.framework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CurrencyUnitContextHolderInterceptor implements HandlerInterceptor {

    private CurrencyUnitContextResolver currencyUnitContextResolver;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, RequestHandler handler) {
        CurrencyUnitContext context = currencyUnitContextResolver.resolveCurrencyUnitContext(request);
        CurrencyUnitContextHolder.setCurrencyUnitContext(context);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, RequestHandler handler,
            Exception ex) throws Exception {
        CurrencyUnitContextHolder.setCurrencyUnitContext(null);
    }

    public CurrencyUnitContextResolver getCurrencyUnitContextResolver() {
        return currencyUnitContextResolver;
    }

    public void setCurrencyUnitContextResolver(CurrencyUnitContextResolver currencyUnitContextResolver) {
        this.currencyUnitContextResolver = currencyUnitContextResolver;
    }

}


