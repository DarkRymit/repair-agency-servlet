package com.epam.finalproject.framework.beans.factory;

import com.epam.finalproject.framework.beans.factory.exception.BeansException;
import com.epam.finalproject.framework.beans.factory.exception.NoSuchBeanDefinitionException;

import java.util.List;
import java.util.Map;

public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    <T> T getBean(String name, Class<T> type) throws BeansException;

    <T> T getBean(Class<T> type) throws BeansException;

    <T> List<T> getBeans(Class<T> type) throws BeansException;

    boolean containsBean(String name);

    boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

    boolean isPrototype(String name) throws NoSuchBeanDefinitionException;

}
