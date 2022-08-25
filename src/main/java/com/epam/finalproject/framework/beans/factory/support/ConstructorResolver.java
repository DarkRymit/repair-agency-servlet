package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.factory.config.BeanDefinition;
import com.epam.finalproject.framework.util.ClassUtils;
import com.epam.finalproject.framework.util.CustomCollectionsUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class ConstructorResolver {

    public static <T> Constructor<T> simpleResolveForClass(Class<T> target) {
        List<Constructor<T>> candidates = Arrays.stream(target.getDeclaredConstructors())
                .map(constructor -> (Constructor<T>) constructor)
                .filter(constructor -> constructor.getParameterCount() > 0)
                .collect(Collectors.toList());
        if (candidates.isEmpty()) {
            try {
                return target.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(e);
            }
        }

        if (candidates.size() == 1) {
            return candidates.get(0);
        }

        Optional<Constructor<T>> optionalConstructor = candidates.stream()
                .filter(constructor -> constructor.isAnnotationPresent(Autowire.class))
                .collect(CustomCollectionsUtil.toOneOrEmpty());
        if (optionalConstructor.isPresent()) {
            return optionalConstructor.orElseThrow();
        }

        throw new NoSuchElementException();
    }

    public static Constructor<?> resolveConstructor(final BeanDefinition definition, List<Object> args) {

        Constructor<?> constructorToUse;
        Class<?> beanClass = definition.getBeanClass();
        try {
            constructorToUse = simpleResolveForClass(beanClass);
        } catch (NoSuchElementException e) {
            List<Constructor<?>> candidates = Arrays.stream(beanClass.getDeclaredConstructors())
                    .filter(constructor -> constructor.getParameterCount() > 0)
                    .collect(Collectors.toList());
            constructorToUse = resolveBestMatch(args, candidates).orElseThrow(
                    () -> new NoSuchElementException("Not found constructor for " + definition.getBeanName()));
        }

        if (constructorToUse.getParameterCount() < args.size()) {
            throw new IllegalArgumentException();
        }
        return constructorToUse;
    }

    public static Optional<Constructor<?>> resolveBestMatch(List<Object> args, List<Constructor<?>> candidates) {
        return candidates.stream().filter(constructor -> {
            Parameter[] parameters = constructor.getParameters();
            for (int i = 0; i < args.size(); i++) {
                Object arg = args.get(i);
                if (!ClassUtils.isAssignableValue(arg, parameters[i].getType())) {
                    return false;
                }
            }
            return true;
        }).max(Comparator.comparingInt(Constructor::getParameterCount));
    }
}
