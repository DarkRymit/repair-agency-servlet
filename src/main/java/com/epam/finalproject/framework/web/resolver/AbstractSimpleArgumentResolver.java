package com.epam.finalproject.framework.web.resolver;

import com.epam.finalproject.framework.web.RequestHandlerContainer;
import com.epam.finalproject.framework.web.WebHttpPair;
import com.epam.finalproject.framework.web.annotation.PathVariable;
import com.epam.finalproject.framework.web.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

public abstract class AbstractSimpleArgumentResolver<T> implements HandlerArgumentResolver{
    @Override
    public Object resolve(WebHttpPair pair, Parameter parameter, RequestHandlerContainer container) {
        HttpServletRequest request = pair.getRequest();
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
        if (requestParam !=null && pathVariable!=null){
            throw new IllegalArgumentException(String.format("Parameter %s have RequestParam and PathVariable in same time",parameter));
        }
        if (requestParam!=null){
            return parseRequest(request, requestParam);
        }else {
            return parsePath(container,pathVariable);
        }

    }

    protected T parsePath(RequestHandlerContainer container, PathVariable pathVariable) {
        String result = container.getPathVariables().get(pathVariable.value());
        if (result == null){
            if (pathVariable.required()){
                throw new RuntimeException();
            }else {
                return getDefaultValue();
            }
        }else{
            return parseString(result);
        }
    }

    protected T parseRequest(HttpServletRequest request, RequestParam requestParam) {
        String result = request.getParameter(requestParam.value());
        if (result == null){
            if (requestParam.required()){
                throw new RuntimeException();
            }else {
                return null;
            }
        }else{
            return parseString(result);
        }
    }

    protected T getDefaultValue() {
        return null;
    }

    protected T parseString(String result) {
        throw new UnsupportedOperationException();
    }
}
