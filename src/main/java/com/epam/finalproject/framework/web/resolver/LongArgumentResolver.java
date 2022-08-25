package com.epam.finalproject.framework.web.resolver;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.util.ClassUtils;

import java.lang.reflect.Parameter;

@Component
public class LongArgumentResolver extends AbstractSimpleArgumentResolver<Long> implements HandlerArgumentResolver{

    @Override
    protected Long parseString(String result) {
        return Long.valueOf(result);
    }

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return ClassUtils.isAssignable(parameter.getType(), Long.class);
    }
}
