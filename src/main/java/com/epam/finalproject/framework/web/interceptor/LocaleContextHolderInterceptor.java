package com.epam.finalproject.framework.web.interceptor;

import com.epam.finalproject.framework.context.i18n.LocaleContext;
import com.epam.finalproject.framework.context.i18n.LocaleContextHolder;
import com.epam.finalproject.framework.web.RequestHandler;
import com.epam.finalproject.framework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

public class LocaleContextHolderInterceptor implements HandlerInterceptor {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LocaleContextHolderInterceptor.class);
    private LocaleContextResolver localeContextResolver;

    public LocaleContextHolderInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, RequestHandler handler) {
        LocaleContext context = localeContextResolver.resolveLocaleContext(request);
        localeContextResolver.setLocaleContext(request,response,context);
        LocaleContextHolder.setLocaleContext(context);
        return true;
    }

    public LocaleContextResolver getLocaleContextResolver() {
        return localeContextResolver;
    }

    public void setLocaleContextResolver(LocaleContextResolver localeContextResolver) {
        this.localeContextResolver = localeContextResolver;
    }
}


