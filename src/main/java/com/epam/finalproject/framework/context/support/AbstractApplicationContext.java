package com.epam.finalproject.framework.context.support;

import com.epam.finalproject.framework.beans.factory.config.BeanDefinition;
import com.epam.finalproject.framework.beans.factory.exception.BeansException;
import com.epam.finalproject.framework.beans.factory.exception.NoSuchBeanDefinitionException;
import com.epam.finalproject.framework.beans.factory.support.ManualConfigurableBeanFactory;
import com.epam.finalproject.framework.context.ApplicationContext;
import com.epam.finalproject.framework.context.ApplicationEvent;
import com.epam.finalproject.framework.context.ApplicationEventMulticaster;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractApplicationContext implements ApplicationContext {

    protected ManualConfigurableBeanFactory factory;

    protected ApplicationEventMulticaster multicaster;

    @Override
    public Object getBean(String name) throws BeansException {
        return factory.getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> type) throws BeansException {
        return factory.getBean(name,type);
    }

    @Override
    public <T> T getBean(Class<T> type) throws BeansException {
        return factory.getBean(type);

    }

    @Override
    public <T> List<T> getBeans(Class<T> type) throws BeansException {
        return factory.getBeans(type);
    }

    @Override
    public <T> List<T> getBeans(Class<T> type, Set<Class<? extends Annotation>> qualifiers) {
        return factory.getBeans(type,qualifiers);
    }

    @Override
    public <T> List<T> getBeans(Set<Class<? extends Annotation>> qualifiers) {
        return factory.getBeans(qualifiers);
    }


    @Override
    public boolean containsBean(String name) {
        return factory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return factory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return factory.isPrototype(name);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions(Class<?> type, Set<Class<? extends Annotation>> qualifiers) {
        return factory.getBeanDefinitions(type,qualifiers);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions(Set<Class<? extends Annotation>> qualifiers) {
        return factory.getBeanDefinitions(qualifiers);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions(Class<?> type) {
        return factory.getBeanDefinitions(type);
    }


    @Override
    public String getApplicationName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getStartupDate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        multicaster.multicastEvent(event);
    }
}
