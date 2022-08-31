package com.epam.finalproject.framework.beans.factory.util;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.beans.annotation.Configuration;
import com.epam.finalproject.framework.beans.annotation.Order;
import com.epam.finalproject.framework.beans.factory.config.BeanDefinition;
import com.epam.finalproject.framework.web.annotation.Controller;
import com.epam.finalproject.framework.web.annotation.Service;

import java.util.Comparator;
import java.util.Optional;

public abstract class BeanUtils {
    private BeanUtils() {
    }

    public static final Comparator<BeanDefinition> beanDefinitionComparator = (o1, o2) -> {
        int o1Order = Optional.ofNullable(o1.getBeanClass().getAnnotation(Order.class))
                .map(Order::value)
                .orElse(Integer.MAX_VALUE);
        int o2Order = Optional.ofNullable(o2.getBeanClass().getAnnotation(Order.class))
                .map(Order::value)
                .orElse(Integer.MAX_VALUE);
        return o1Order - o2Order;
    };

    public static boolean isPossiblyBean(Class<?> targetClass){
        return !targetClass.isAnnotation() && (targetClass.isAnnotationPresent(
                Component.class) || targetClass.isAnnotationPresent(Configuration.class) || targetClass.isAnnotationPresent(
                Controller.class) || targetClass.isAnnotationPresent(Service.class));
    }
}
