package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.framework.beans.factory.ListableBeanFactory;
import com.epam.finalproject.framework.beans.factory.config.BeanDefinition;
import com.epam.finalproject.framework.beans.factory.exception.BeansException;
import org.slf4j.Logger;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ListableBeanFactory {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AbstractBeanFactory.class);

    protected <T> T doGetBean(String name, Class<T> type, Set<Class<? extends Annotation>> qualifiers) throws BeansException {
        throw new UnsupportedOperationException();
    }

    protected <T> List<T> doGetBeans(Class<T> type, Set<Class<? extends Annotation>> qualifiers) throws BeansException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null, null);
    }

    @Override
    public <T> T getBean(String name, Class<T> type) throws BeansException {
        return doGetBean(name, type, null);
    }

    @Override
    public <T> T getBean(Class<T> type) throws BeansException {
        return doGetBean(null, type, null);
    }

    @Override
    public <T> List<T> getBeans(Class<T> type) throws BeansException {
        return doGetBeans(type, null);
    }

    @Override
    public <T> List<T> getBeans(Class<T> type, Set<Class<? extends Annotation>> qualifiers) throws BeansException {
        return doGetBeans(type, qualifiers);
    }

    @Override
    public <T> List<T> getBeans(Set<Class<? extends Annotation>> qualifiers) {
        return doGetBeans(null, qualifiers);
    }

    public BeanDefinition getBeanDefinition(String name) {
        throw new UnsupportedOperationException();
    }

    protected Object getCachedOrCreateSingletonBean(BeanDefinition definition) {
        log.trace("Call getCachedOrCreateSingletonBean with definition {} ",definition);
        Object result = getSingleton(definition.getBeanName());
        if (result == null) {
            Object singleton = setupBean(definition);
            registerSingleton(definition.getBeanName(), singleton);
            return singleton;
        } else {
            return result;
        }
    }

    protected Object setupBean(BeanDefinition definition) {
        throw new UnsupportedOperationException();
    }

    protected <T> T getBeanByDefinition(BeanDefinition definition, Class<T> cast) {
        log.trace("Call getBeanByDefinition with definition {} and cast {} ",definition,cast);
        Object result;
        if (definition.isSingleton()) {
            result = getCachedOrCreateSingletonBean(definition);
        } else {
            result = setupBean(definition);
        }
        try {
            return (T) result;
        } catch (ClassCastException e) {
            throw new BeansException(e);
        }
    }
}
