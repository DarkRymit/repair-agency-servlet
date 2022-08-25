package com.epam.finalproject.framework.web.resolver;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.web.RequestHandlerContainer;
import com.epam.finalproject.framework.web.WebHttpPair;
import com.epam.finalproject.framework.web.annotation.RequestJsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.lang.reflect.Parameter;

@Component
public class JsonObjectResolver implements HandlerArgumentResolver {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JsonObjectResolver.class);

    @Override
    public Object resolve(WebHttpPair pair, Parameter parameter, RequestHandlerContainer handler) {
        RequestJsonObject autoResolve = parameter.getAnnotation(RequestJsonObject.class);
        Class<?> targetClass = getTargetClass(parameter, autoResolve);
        StringBuilder jb = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = pair.getRequest().getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory().constructType(targetClass);
        Object result = null;
        try {
            result = objectMapper.readValue(String.valueOf(jb), type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.trace("Resolver {} resolve value {}",this.getClass(),result);
        return result;
    }

    private Class<?> getTargetClass(Parameter parameter, RequestJsonObject autoResolve) {
        Class<?> targetClass;
        if (!autoResolve.value().equals(RequestJsonObject.DEFAULT.class)) {
            targetClass = autoResolve.value();
        } else {
            if (!parameter.getType().isInterface()) {
                targetClass = parameter.getType();
            } else {
                throw new UnsupportedOperationException(String.format("Interface %s not supported in resolver %s", parameter.getType(), this.getClass()));
            }
        }
        return targetClass;
    }

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestJsonObject.class);
    }
}
