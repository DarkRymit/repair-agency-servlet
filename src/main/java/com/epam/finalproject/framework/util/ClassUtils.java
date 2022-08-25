package com.epam.finalproject.framework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {

    public static boolean isAssignableValue(Object value, Class<?> type) {
        return value != null ? isAssignable(value.getClass(), type) : !type.isPrimitive();
    }

    public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
        return org.apache.commons.lang3.ClassUtils.isAssignable(lhsType,rhsType);
    }

    public static Method getDistinctMethodAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        Method resultMethod = null;
        Class<?> clazz = type;
        while (clazz != Object.class) {
            for (final Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    if (resultMethod == null){
                        resultMethod = method;
                    }else {
                        throw new IllegalStateException("Multiple annotation" + annotation.getSimpleName() + "found for" + type);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return resultMethod;
    }
}
