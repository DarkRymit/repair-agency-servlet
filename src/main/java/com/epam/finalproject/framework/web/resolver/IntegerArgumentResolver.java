package com.epam.finalproject.framework.web.resolver;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.util.ClassUtils;

import java.lang.reflect.Parameter;

@Component
public class IntegerArgumentResolver extends  AbstractSimpleArgumentResolver<Integer>{

    @Override
    protected Integer parseString(String result) {
        return Integer.valueOf(result);
    }

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return ClassUtils.isAssignable(parameter.getType(), Integer.class);
    }
}
