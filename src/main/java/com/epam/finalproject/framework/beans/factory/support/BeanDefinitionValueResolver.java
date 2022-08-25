package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.framework.beans.factory.ListableBeanFactory;
import com.epam.finalproject.framework.beans.factory.config.BeanDefinitionsReference;
import com.epam.finalproject.framework.beans.factory.config.BeanReference;
import com.epam.finalproject.framework.beans.factory.config.BeansReference;

import java.util.ArrayList;

public class BeanDefinitionValueResolver {
    private final ListableBeanFactory beanFactory;

    public BeanDefinitionValueResolver(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof BeanReference) {
            BeanReference ref = (BeanReference) value;
            return beanFactory.getBean(ref.getName(), ref.getClazz());
        }
        else if (value instanceof BeansReference) {
            BeansReference ref = (BeansReference) value;
            return new ArrayList<>(beanFactory.getBeans(ref.getClazz(), ref.getQualifiers()));
        }else if (value instanceof BeanDefinitionsReference) {
            BeanDefinitionsReference ref = (BeanDefinitionsReference) value;
            return new ArrayList<>(beanFactory.getBeanDefinitions(ref.getClazz(), ref.getQualifiers()));
        }
        else{
            return value;
        }
    }
}
