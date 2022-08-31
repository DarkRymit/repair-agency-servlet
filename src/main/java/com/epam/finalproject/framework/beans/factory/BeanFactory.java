package com.epam.finalproject.framework.beans.factory;

import com.epam.finalproject.framework.beans.factory.exception.BeansException;
import com.epam.finalproject.framework.beans.factory.exception.NoSuchBeanDefinitionException;

import java.util.List;

/**
 * The interface Bean factory.
 */
public interface BeanFactory {

    /**
     * Gets bean.
     *
     * @param name the name
     * @return the bean
     * @throws BeansException if factory cannot return object
     */
    Object getBean(String name) throws BeansException;

    /**
     * Gets bean.
     *
     * @param name the name
     * @param type the type
     * @return the bean
     * @throws BeansException if factory cannot return object
     */
    <T> T getBean(String name, Class<T> type) throws BeansException;

    /**
     * Gets bean.
     *
     * @param type the type
     * @return the bean
     * @throws BeansException if factory cannot return object
     */
    <T> T getBean(Class<T> type) throws BeansException;

    /**
     * Gets beans.
     *
     * @param type the type
     * @return {@link List} of beans or empty list if no beans
     * @throws BeansException if factory cannot return object
     */
    <T> List<T> getBeans(Class<T> type) throws BeansException;

    /**
     * Contains bean boolean.
     *
     * @param name the name
     * @return {@code true} if beanDefinition registered in factory or {@code false} if not registered
     */
    boolean containsBean(String name);

    /**
     * Is singleton boolean.
     *
     * @param name the name
     * @return {@code true} if bean of that name has scope singleton or {@code false} if another scope
     * @throws NoSuchBeanDefinitionException if bean by name not exist
     */
    boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

    /**
     * Is prototype boolean.
     *
     * @param name the name
     * @return {@code true} if bean of that name has scope prototype or {@code false} if another scope
     * @throws NoSuchBeanDefinitionException if bean by name not exist
     */
    boolean isPrototype(String name) throws NoSuchBeanDefinitionException;

}
