package com.epam.finalproject.framework.web.resolver;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.ui.RedirectAttributes;
import com.epam.finalproject.framework.ui.RedirectAttributesModelMap;
import com.epam.finalproject.framework.util.ClassUtils;
import com.epam.finalproject.framework.web.RequestHandlerContainer;
import com.epam.finalproject.framework.web.WebHttpPair;
import com.epam.finalproject.framework.web.support.RedirectAttributesContext;

import java.lang.reflect.Parameter;

@Component
public class RedirectAttributesResolver implements HandlerArgumentResolver{
    public static final String REDIRECT_ATTRIBUTE = RedirectAttributes.class.toString();
    @Override
    public Object resolve(WebHttpPair pair, Parameter parameter, RequestHandlerContainer handler) {
        RedirectAttributes attributes = new RedirectAttributesModelMap();
        RedirectAttributesContext.setRedirectAttributes(attributes);
        pair.getRequest().setAttribute(REDIRECT_ATTRIBUTE,attributes);
        return attributes;
    }

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return ClassUtils.isAssignable(parameter.getType(), RedirectAttributes.class);
    }
}
