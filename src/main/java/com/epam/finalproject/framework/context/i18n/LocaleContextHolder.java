//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.epam.finalproject.framework.context.i18n;

import java.util.Locale;
import java.util.TimeZone;

public final class LocaleContextHolder {
    private static final ThreadLocal<LocaleContext> LOCALE_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();


    private LocaleContextHolder() {
    }

    public static void resetLocaleContext() {
        LOCALE_CONTEXT_THREAD_LOCAL.remove();
    }


    public static void setLocaleContext(LocaleContext localeContext) {
        if (localeContext == null) {
            resetLocaleContext();
        } else {
            LOCALE_CONTEXT_THREAD_LOCAL.set(localeContext);
        }

    }

    public static LocaleContext getLocaleContext() {
        return LOCALE_CONTEXT_THREAD_LOCAL.get();
    }


    public static void setLocale(Locale locale) {
        LocaleContext localeContext;
        if (locale != null) {
            localeContext = new SimpleLocaleContext(locale);
        } else {
            localeContext = null;
        }

        setLocaleContext(localeContext);
    }

    public static Locale getLocale() {
        return getLocale(getLocaleContext());
    }

    public static Locale getLocale(LocaleContext localeContext) {
        if (localeContext != null) {
            Locale locale = localeContext.getLocale();
            if (locale != null) {
                return locale;
            }
        }

        return Locale.getDefault();
    }


    public static TimeZone getTimeZone() {
        return getTimeZone(getLocaleContext());
    }

    public static TimeZone getTimeZone(LocaleContext localeContext) {
        return TimeZone.getDefault();
    }
}
