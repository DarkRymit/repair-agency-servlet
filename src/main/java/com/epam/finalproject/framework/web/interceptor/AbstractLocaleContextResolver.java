package com.epam.finalproject.framework.web.interceptor;

import com.epam.finalproject.currency.context.SimpleCurrencyUnitContext;
import com.epam.finalproject.currency.servlet.CurrencyUnitContextResolver;
import com.epam.finalproject.framework.context.i18n.SimpleLocaleContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.money.CurrencyUnit;
import java.util.Locale;

public abstract class AbstractLocaleContextResolver extends AbstractLocaleResolver implements LocaleContextResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return resolveLocaleContext(request).getLocale();
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        setLocaleContext(request,response,new SimpleLocaleContext(locale));
    }

}

