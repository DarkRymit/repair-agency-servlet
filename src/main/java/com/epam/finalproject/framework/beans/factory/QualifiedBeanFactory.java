package com.epam.finalproject.framework.beans.factory;


import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface QualifiedBeanFactory extends BeanFactory {

    <T> List<T> getBeans(Class<T> type, Set<Class<? extends Annotation>> qualifiers);

    <T> List<T> getBeans(Set<Class<? extends Annotation>> qualifiers);
}
