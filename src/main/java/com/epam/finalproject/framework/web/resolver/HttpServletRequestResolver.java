package com.epam.finalproject.framework.web.resolver;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.util.ClassUtils;
import com.epam.finalproject.framework.web.RequestHandlerContainer;
import com.epam.finalproject.framework.web.WebHttpPair;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

@Component
public class HttpServletRequestResolver implements HandlerArgumentResolver {
    @Override
    public Object resolve(WebHttpPair pair, Parameter parameter, RequestHandlerContainer handler) {
        return pair.getRequest();
    }

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return ClassUtils.isAssignable(parameter.getType(), HttpServletRequest.class);
    }
}
