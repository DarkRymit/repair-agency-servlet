package com.epam.finalproject.framework.security.web;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.security.AccessDeniedException;
import com.epam.finalproject.framework.security.annotation.PostAuthorize;
import com.epam.finalproject.framework.security.annotation.PreAuthorize;
import com.epam.finalproject.framework.security.support.SecurityContextHolder;
import com.epam.finalproject.framework.security.support.SecurityExpressionSupport;
import com.epam.finalproject.framework.web.RequestHandler;
import com.epam.finalproject.framework.web.servlet.HandlerInterceptor;
import com.epam.finalproject.framework.web.servlet.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.epam.finalproject.framework.security.util.SecurityUtils.evalExpression;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            RequestHandler handler) throws Exception {
        Method method = handler.getTargetMethod();
        Class<?> topClass = method.getDeclaringClass();
        PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
        PreAuthorize preAuthorizeClass = topClass.getAnnotation(PreAuthorize.class);

        if (preAuthorizeClass == null && preAuthorize == null) {
            return true;
        }
        String expression = getExpression(preAuthorize, preAuthorizeClass);

        if (expression.isBlank()) {
            return true;
        }

        SecurityExpressionSupport support = new SecurityExpressionSupport(SecurityContextHolder.getContext());
        Map<String,Object> contextMap = new HashMap<>();
        contextMap.put("req",request);
        contextMap.put("res",response);
        boolean result = evalExpression(expression, support,contextMap);
        if (result) {
            return true;
        }

        if (support.isAnonymous()) {
            response.sendRedirect(request.getContextPath() + "/auth/signin");
            return false;
        }

        throw new AccessDeniedException(String.format("Denied for %s", support.getAuthentication()));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, RequestHandler handler,
            View view) throws Exception {
        Method method = handler.getTargetMethod();
        Class<?> topClass = method.getDeclaringClass();
        PostAuthorize postAuthorize = method.getAnnotation(PostAuthorize.class);
        PostAuthorize postAuthorizeClass = topClass.getAnnotation(PostAuthorize.class);

        if (postAuthorizeClass == null && postAuthorize == null) {
            return;
        }

        String expression = String.join(" && ", getExpression(postAuthorize, postAuthorizeClass));

        if (expression.isBlank()) {
            return;
        }

        SecurityExpressionSupport support = new SecurityExpressionSupport(SecurityContextHolder.getContext());
        Map<String,Object> contextMap = new HashMap<>();
        contextMap.put("req",request);
        contextMap.put("res",response);
        contextMap.put("view",view);

        boolean result = evalExpression(expression, support,contextMap);
        if (result) {
            return;
        }

        if (support.isAnonymous()) {
            response.sendRedirect(request.getContextPath() + "/auth/signin");
            return;
        }

        throw new AccessDeniedException(String.format("Denied for %s", support.getAuthentication()));

    }

    private String getExpression(PreAuthorize preAuthorize, PreAuthorize preAuthorizeClass) {
        String clazzPart = "";
        String methodPart = "";

        if (preAuthorizeClass != null && !preAuthorizeClass.value().isBlank()) {
            clazzPart = preAuthorizeClass.value().trim();
        }
        if (preAuthorize != null && !preAuthorize.value().isBlank()) {
            methodPart = preAuthorize.value().trim();
        }

        return getWrappedOrEmptyString(clazzPart, methodPart);
    }
    private String getExpression(PostAuthorize postAuthorize, PostAuthorize postAuthorizeClass) {

        String clazzPart = "";
        String methodPart = "";

        if (postAuthorizeClass != null && !postAuthorizeClass.value().isBlank()) {
            clazzPart = postAuthorizeClass.value().trim();
        }
        if (postAuthorize != null && !postAuthorize.value().isBlank()) {
            methodPart = postAuthorize.value().trim();
        }

        return getWrappedOrEmptyString(clazzPart, methodPart);
    }

    private String getWrappedOrEmptyString(String clazzPart, String methodPart) {
        if (clazzPart.isBlank()&& methodPart.isBlank()){
            return "";
        }

        if (clazzPart.isBlank()){
            return methodPart;
        }

        if (methodPart.isBlank()){
            return clazzPart;
        }

        return clazzPart + " && (" + methodPart + ")";
    }


}
