package com.epam.finalproject.framework.beans.factory.config;

import com.epam.finalproject.framework.beans.factory.exception.BeansException;

public interface BeanPostProcessor {
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
