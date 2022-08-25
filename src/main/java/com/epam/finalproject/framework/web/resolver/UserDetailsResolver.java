package com.epam.finalproject.framework.web.resolver;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.security.Authentication;
import com.epam.finalproject.framework.security.UserDetails;
import com.epam.finalproject.framework.security.support.SecurityContextHolder;
import com.epam.finalproject.framework.util.ClassUtils;
import com.epam.finalproject.framework.web.RequestHandlerContainer;
import com.epam.finalproject.framework.web.WebHttpPair;

import java.lang.reflect.Parameter;

@Component
public class UserDetailsResolver implements HandlerArgumentResolver {
    @Override
    public Object resolve(WebHttpPair pair, Parameter parameter, RequestHandlerContainer handler) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails){
            return authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return ClassUtils.isAssignable(parameter.getType(), UserDetails.class);
    }
}
