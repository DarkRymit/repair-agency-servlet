package com.epam.finalproject.framework.web.interceptor;

import java.util.Locale;

public abstract class AbstractLocaleResolver implements LocaleResolver {
    private Locale defaultLocale;

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

}

