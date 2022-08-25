package com.epam.finalproject.framework.web.interceptor;

import com.epam.finalproject.framework.context.i18n.LocaleContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Locale;

public class SessionLocaleResolver extends AbstractLocaleContextResolver implements LocaleContextResolver {

    public static final String LOCALE_SESSION_ATTRIBUTE_NAME = SessionLocaleResolver.class.getSimpleName() + ".LOCALE";
    private String localeSessionAttributeName = LOCALE_SESSION_ATTRIBUTE_NAME;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = (Locale)request.getSession().getAttribute(localeSessionAttributeName);
        if (locale == null) {
            locale = getDefaultLocale();
        }
        return locale;
    }

    @Override
    public LocaleContext resolveLocaleContext(HttpServletRequest request) {
        return () -> resolveLocale(request);
    }

    @Override
    public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext context) {
        request.getSession().setAttribute(localeSessionAttributeName, context.getLocale());
    }

    public String getLocaleSessionAttributeName() {
        return localeSessionAttributeName;
    }

    public void setLocaleSessionAttributeName(String localeSessionAttributeName) {
        this.localeSessionAttributeName = localeSessionAttributeName;
    }
}

