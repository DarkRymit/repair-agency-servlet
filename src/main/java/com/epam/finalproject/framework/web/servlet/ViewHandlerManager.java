package com.epam.finalproject.framework.web.servlet;

import com.epam.finalproject.framework.beans.annotation.Beans;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.web.WebHttpPair;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewHandlerManager {
    Map<Class<?>, ViewHandler<View>> handlers;

    public ViewHandlerManager(@Beans(ViewHandler.class) List<ViewHandler<View>> handlers) {
        Map<Class<?>, ViewHandler<View>> handlerMap = new HashMap<>();
        handlers.forEach(viewHandler -> handlerMap.put(getTargetType(viewHandler.getClass()), viewHandler));
        this.handlers = handlerMap;
    }


    public void handle(View view, WebHttpPair pair) {
        ViewHandler<View> handler = handlers.get(view.getClass());
        handler.handle(view,pair);
    }

    private Class<?> getTargetType(Class<?> handler) {
        List<ParameterizedType> parameterizedTypes = Arrays.stream(handler.getGenericInterfaces())
                .filter(ParameterizedType.class::isInstance)
                .map(ParameterizedType.class::cast)
                .filter(t -> t.getRawType().equals(ViewHandler.class))
                .collect(Collectors.toList());
        if (parameterizedTypes.size() != 1) {
            throw new IllegalStateException("Parametrized type is not 1 on " + handler.getSimpleName());
        }
        return (Class<?>) parameterizedTypes.get(0).getActualTypeArguments()[0];
    }
}
