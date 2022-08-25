package com.epam.finalproject.framework.web.support;

import com.epam.finalproject.framework.beans.factory.BeanFactory;
import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Beans;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.util.CustomCollectionsUtil;
import com.epam.finalproject.framework.web.RequestHandlerContainer;
import com.epam.finalproject.framework.web.WebHttpPair;
import com.epam.finalproject.framework.web.resolver.HandlerArgumentResolver;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestArgumentAdapter {

    List<HandlerArgumentResolver> resolvers;

    BeanFactory factory;


    @Autowire
    public RequestArgumentAdapter(BeanFactory factory, @Beans(HandlerArgumentResolver.class) List<HandlerArgumentResolver> resolvers) {
        this.factory = factory;
        this.resolvers = resolvers;
    }

    public List<Object> adapt(WebHttpPair pair, RequestHandlerContainer handlerContainer) {
        List<Object> args = new LinkedList<>();
        List<Parameter> parameters = Arrays.stream(handlerContainer.getHandler().getTargetMethod().getParameters())
                .collect(Collectors.toList());
        for (Parameter parameter : parameters) {

            HandlerArgumentResolver resolver = resolvers.stream()
                    .filter(r -> r.supportsParameter(parameter))
                    .collect(CustomCollectionsUtil.toOneOrEmpty())
                    .orElseThrow();
            args.add(resolver.resolve(pair,parameter,handlerContainer));
        }

        return args;
    }
}
