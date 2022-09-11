package com.epam.finalproject.framework.web.interceptor;

import com.epam.finalproject.framework.context.i18n.LocaleContext;
import com.epam.finalproject.framework.context.i18n.LocaleContextHolder;
import com.epam.finalproject.framework.web.RequestHandler;
import com.epam.finalproject.framework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LocaleContextHolderInterceptor implements HandlerInterceptor {

    private LocaleContextResolver localeContextResolver;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, RequestHandler handler) {
        LocaleContext context = localeContextResolver.resolveLocaleContext(request);
        localeContextResolver.setLocaleContext(request,response,context);
        LocaleContextHolder.setLocaleContext(context);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, RequestHandler handler,
            Exception ex) {
        LocaleContextHolder.setLocaleContext(null);
    }

    public LocaleContextResolver getLocaleContextResolver() {
        return localeContextResolver;
    }

    public void setLocaleContextResolver(LocaleContextResolver localeContextResolver) {
        this.localeContextResolver = localeContextResolver;
    }
}


