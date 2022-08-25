package com.epam.finalproject.framework.web.servlet;

import com.epam.finalproject.framework.web.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HandlerInterceptor {

    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, RequestHandler handler) throws Exception {
        return true;
    }

    default void postHandle(HttpServletRequest request, HttpServletResponse response, RequestHandler handler, View view) throws Exception {
    }

    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, RequestHandler handler, Exception ex) throws Exception {
    }
}
