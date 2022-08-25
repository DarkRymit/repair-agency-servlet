package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.framework.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final Map<String, Object> singletonBeanMap = new ConcurrentHashMap<>();

    public void registerSingleton(String beanName, Object singletonObject) {

        Objects.requireNonNull(beanName, "'beanName' must not be null");

        Object oldObject = this.singletonBeanMap.get(beanName);
        if (oldObject != null) {
            throw new IllegalStateException("Could not register object [" + singletonObject +
                    "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
        }
        this.singletonBeanMap.put(beanName, singletonObject);

    }

    public Object getSingleton(String beanName) {
        return this.singletonBeanMap.get(beanName);
    }

}
