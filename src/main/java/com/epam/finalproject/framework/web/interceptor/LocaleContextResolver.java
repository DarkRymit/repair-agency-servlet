package com.epam.finalproject.framework.web.interceptor;

import com.epam.finalproject.currency.context.CurrencyUnitContext;
import com.epam.finalproject.framework.context.i18n.LocaleContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LocaleContextResolver extends LocaleResolver {
    LocaleContext resolveLocaleContext(HttpServletRequest request);
    void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext context);
}
