package com.epam.finalproject.framework.web.resolver;

import com.epam.finalproject.framework.web.RequestHandlerContainer;
import com.epam.finalproject.framework.web.WebHttpPair;

import java.lang.reflect.Parameter;

public interface HandlerArgumentResolver {
    Object resolve(WebHttpPair webHttpPair, Parameter parameter, RequestHandlerContainer handler);
    boolean supportsParameter(Parameter parameter);
}
