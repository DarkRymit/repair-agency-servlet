package com.epam.finalproject.framework.beans.factory;

import com.epam.finalproject.framework.beans.factory.config.BeanDefinition;
import com.epam.finalproject.framework.beans.factory.exception.BeansException;
import com.epam.finalproject.framework.beans.factory.exception.NoSuchBeanDefinitionException;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ListableBeanFactory extends QualifiedBeanFactory {

    List<BeanDefinition> getBeanDefinitions(Class<?> type, Set<Class<? extends Annotation>> qualifiers);

    List<BeanDefinition> getBeanDefinitions(Set<Class<? extends Annotation>> qualifiers);


    List<BeanDefinition> getBeanDefinitions(Class<?> type);

}