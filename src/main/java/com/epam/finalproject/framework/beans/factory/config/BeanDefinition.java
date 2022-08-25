package com.epam.finalproject.framework.beans.factory.config;

import java.util.Set;
import java.util.function.Supplier;

public interface BeanDefinition {

    String getBeanName();

    void setBeanName(String name);

    Class<?> getBeanClass();

    void setBeanClass(Class<?> name);

    String getScope();

    void setScope(String scope);

    Set<Class<?>> getBeanQualifiers();

    void setBeanQualifiers(Set<Class<?>> beanQualifiers);

    String getConfigBeanName();

    void setConfigBeanName(String method);

    String getFactoryMethod();

    void setFactoryMethod(String method);

    Supplier<?> getSupplier();

    void setSupplier(Supplier<?> method);

    ConstructArguments getConstructArguments();

    default boolean hasConstructArguments() {
        return !this.getConstructArguments().isEmpty();
    }

    PropertyValues getPropertyValues();

    default boolean hasPropertyValues() {
        return !this.getPropertyValues().isEmpty();
    }

    String getInitMethodName();

    void setInitMethodName(String var1);

    String getDestroyMethodName();

    void setDestroyMethodName(String var1);

    boolean isSingleton();

    boolean isPrototype();

}
