package com.epam.finalproject.framework.web.interceptor;

import com.epam.finalproject.framework.web.RequestHandler;
import com.epam.finalproject.framework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

import java.util.Locale;

public class LocaleChangeInterceptor implements HandlerInterceptor {
    public static final String DEFAULT_PARAM_NAME = "language";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LocaleChangeInterceptor.class);
    private String paramName = DEFAULT_PARAM_NAME;

    private LocaleResolver localeResolver;

    private boolean ignoreInvalidLocale = false;

    public LocaleChangeInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, RequestHandler handler) {
        String newLocale = request.getParameter(paramName);
        if (newLocale != null) {
            try {
                localeResolver.setLocale(request, response, parseLocaleValue(newLocale));
            } catch (IllegalArgumentException e) {
                if (!isIgnoreInvalidLocale()) {
                    throw e;
                }
                log.debug("Ignoring invalid locale value [{}]: ",newLocale,e);
            }
        }
        return true;
    }

    protected Locale parseLocaleValue(String localeValue) {
        return Locale.forLanguageTag(localeValue);
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public LocaleResolver getLocaleResolver() {
        return localeResolver;
    }

    public void setLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    public boolean isIgnoreInvalidLocale() {
        return ignoreInvalidLocale;
    }

    public void setIgnoreInvalidLocale(boolean ignoreInvalidLocale) {
        this.ignoreInvalidLocale = ignoreInvalidLocale;
    }
}


