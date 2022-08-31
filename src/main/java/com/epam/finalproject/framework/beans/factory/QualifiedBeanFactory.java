package com.epam.finalproject.framework.beans.factory;


import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

/**
 * The interface Qualified bean factory.
 */
public interface QualifiedBeanFactory extends BeanFactory {

    /**
     * Gets beans.
     *
     * @param type       the type
     * @param qualifiers the qualifiers
     * @return the beans
     */
    <T> List<T> getBeans(Class<T> type, Set<Class<? extends Annotation>> qualifiers);

    /**
     * Gets beans.
     *
     * @param qualifiers the qualifiers
     * @return the beans
     */
    <T> List<T> getBeans(Set<Class<? extends Annotation>> qualifiers);
}
