package com.epam.finalproject.framework.web.support;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.BeanDefinitions;
import com.epam.finalproject.framework.beans.annotation.Beans;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.beans.factory.BeanFactory;
import com.epam.finalproject.framework.beans.factory.config.BeanDefinition;
import com.epam.finalproject.framework.web.*;
import com.epam.finalproject.framework.web.annotation.*;
import com.epam.finalproject.framework.web.servlet.HandlerInterceptor;
import com.epam.finalproject.framework.web.servlet.View;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.executable.ExecutableValidator;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RequestHandlerManager {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RequestHandlerManager.class);
    final BeanFactory factory;

    final List<BeanDefinition> controllerBeanDefinitions;


    final List<HandlerInterceptor> handlerInterceptors;

    final RequestHandlerResolver resolver;

    final ExecutableValidator executableValidator;


    @Autowire
    public RequestHandlerManager(@BeanDefinitions @Controller List<BeanDefinition> controllerBeanDefinitions,
            BeanFactory factory, RequestHandlerResolver resolver,
            @Beans(HandlerInterceptor.class) List<HandlerInterceptor> handlerInterceptors,
            ExecutableValidator executableValidator) {
        this.factory = factory;
        this.resolver = resolver;
        this.controllerBeanDefinitions = controllerBeanDefinitions;
        this.handlerInterceptors = handlerInterceptors;
        this.executableValidator = executableValidator;
    }

    public RequestHandlerManager addHandler(RequestHandler handler) {
        resolver.addHandler(handler);
        return this;
    }

    public RequestHandlerContainer resolve(HttpServletRequest request) {
        return resolver.resolve(request).orElse(null);
    }

    public boolean preHandle(RequestHandler handler, WebHttpPair pair) throws Exception {
        log.trace("Call preHandle with handler {}", handler);
        boolean result = true;
        for (HandlerInterceptor interceptor : handlerInterceptors) {
            if (!interceptor.preHandle(pair.getRequest(), pair.getResponse(), handler)) {
                result = false;
                break;
            }
        }
        return result;
    }

    public void postHandle(RequestHandler handler, WebHttpPair pair, View view) throws Exception {
        log.trace("Call postHandle with handler {}", handler);
        for (HandlerInterceptor interceptor : handlerInterceptors) {
            interceptor.postHandle(pair.getRequest(), pair.getResponse(), handler, view);
        }
    }

    public void afterCompletion(RequestHandler handler, WebHttpPair pair, Exception e) throws Exception {
        log.trace("Call afterCompletion with handler {}", handler);
        for (HandlerInterceptor interceptor : handlerInterceptors) {
            interceptor.afterCompletion(pair.getRequest(), pair.getResponse(), handler, e);
        }
    }


    public Object invoke(RequestHandler handler, List<Object> args) throws Exception {
        log.trace("Call invoke with handler {} args {}", handler, args);
        Object controller = factory.getBean(handler.getControllerBeanName());
        log.trace("found controller {}", controller);
        Set<ConstraintViolation<Object>> violations
                = executableValidator.validateParameters(controller, handler.getTargetMethod(), args.toArray());
        if (!violations.isEmpty()) {
            throw new HandlerArgumentValidationException(String.format("Not valid %s",violations));
        }
        try {
            Method method = handler.getTargetMethod();
            method.setAccessible(true);
            Object result = method.invoke(controller, args.toArray());
            log.trace("invoke on method {} with controller {} with args {}", method, controller, args);
            return result;
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Exception) {
                throw (Exception) e.getCause();
            }
            throw new ServletException(e.getCause());
        }
    }

    @PostConstruct
    public void setup() {
        for (BeanDefinition beanDefinition : controllerBeanDefinitions) {
            List<Method> targetMethods = Arrays.stream(beanDefinition.getBeanClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class) || method.isAnnotationPresent(
                            GetMapping.class) || method.isAnnotationPresent(PostMapping.class))
                    .collect(Collectors.toList());
            List<RequestHandler> handlers = targetMethods.stream()
                    .map(method -> parse(method, beanDefinition))
                    .collect(Collectors.toList());
            handlers.forEach(this::addHandler);
        }
    }

    private RequestHandler parse(Method method, BeanDefinition controller) {
        List<String> patterns;
        Set<String> methods;
        RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        if (mapping == null) {
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            if (getMapping == null) {
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                if (postMapping == null) {
                    throw new IllegalArgumentException();
                } else {
                    patterns = Arrays.stream(postMapping.value()).collect(Collectors.toList());
                    methods = Set.of(RequestMethod.POST.name());
                }
            } else {
                patterns = Arrays.stream(getMapping.value()).collect(Collectors.toList());
                methods = Set.of(RequestMethod.GET.name());
            }
        } else {
            patterns = Arrays.stream(mapping.value()).collect(Collectors.toList());
            methods = Arrays.stream(mapping.method()).map(Enum::name).collect(Collectors.toSet());
        }


        RequestMapping classMapping = controller.getBeanClass().getAnnotation(RequestMapping.class);
        String prefix = "";
        if (classMapping != null) {
            prefix = classMapping.value()[0];
        }
        String finalPrefix = prefix;
        Set<URLPattern> urlPatterns = patterns.stream()
                .map(s -> finalPrefix + s)
                .map(URLPatternUtil::buildPattern)
                .collect(Collectors.toSet());
        RequestMappingInfo mappingInfo = new RequestMappingInfo(urlPatterns, methods);
        return new RequestHandler(method, controller.getBeanName(), mappingInfo);
    }
}
