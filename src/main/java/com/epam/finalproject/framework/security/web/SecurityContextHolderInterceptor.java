package com.epam.finalproject.framework.security.web;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.security.support.SecurityContextHolder;
import com.epam.finalproject.framework.web.RequestHandler;
import com.epam.finalproject.framework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityContextHolderInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, RequestHandler handler,
            Exception ex) {
        SecurityContextHolder.resetContext();
    }
}
