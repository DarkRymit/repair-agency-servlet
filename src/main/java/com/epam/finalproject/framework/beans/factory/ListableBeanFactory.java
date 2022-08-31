package com.epam.finalproject.framework.beans.factory;

import com.epam.finalproject.framework.beans.factory.config.BeanDefinition;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

/**
 * The interface Listable bean factory.
 */
public interface ListableBeanFactory extends QualifiedBeanFactory {

    /**
     * Gets bean definitions.
     *
     * @param type       the type
     * @param qualifiers the qualifiers
     * @return {@link List} of {@link BeanDefinition} or empty list if no beans
     */
    List<BeanDefinition> getBeanDefinitions(Class<?> type, Set<Class<? extends Annotation>> qualifiers);

    /**
     * Gets bean definitions.
     *
     * @param qualifiers the qualifiers
     * @return {@link List} of {@link BeanDefinition} or empty list if no beans
     */
    List<BeanDefinition> getBeanDefinitions(Set<Class<? extends Annotation>> qualifiers);


    /**
     * Gets bean definitions.
     *
     * @param type the type
     * @return {@link List} of {@link BeanDefinition} or empty list if no beans
     */
    List<BeanDefinition> getBeanDefinitions(Class<?> type);

}