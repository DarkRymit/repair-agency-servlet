package com.epam.finalproject.framework.context.i18n;

import java.util.Locale;

public class SimpleLocaleContext implements LocaleContext {
    private final Locale locale;

    public SimpleLocaleContext( Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String toString() {
        return this.locale != null ? this.locale.toString() : "-";
    }
}