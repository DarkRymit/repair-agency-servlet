package com.epam.finalproject.framework.web.resolver;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.util.ClassUtils;

import java.lang.reflect.Parameter;

@Component
public class StringArgumentResolver extends AbstractSimpleArgumentResolver<String> implements HandlerArgumentResolver{

    @Override
    protected String parseString(String result) {
        return result;
    }

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return ClassUtils.isAssignable(parameter.getType(), String.class);
    }
}
