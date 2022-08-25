package com.epam.finalproject.framework.web.resolver;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.util.ClassUtils;
import com.epam.finalproject.framework.web.RequestHandlerContainer;
import com.epam.finalproject.framework.web.WebHttpPair;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Parameter;

@Component
public class HttpServletResponseResolver implements HandlerArgumentResolver{
    @Override
    public Object resolve(WebHttpPair pair, Parameter parameter, RequestHandlerContainer handler) {
        return pair.getResponse();
    }

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return ClassUtils.isAssignable(parameter.getType(), HttpServletResponse.class);
    }
}
