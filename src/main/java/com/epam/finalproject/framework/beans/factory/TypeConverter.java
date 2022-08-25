package com.epam.finalproject.framework.beans.factory;

import com.epam.finalproject.framework.util.ClassUtils;

public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;

    <T> boolean isConversionAvailable(Object value, Class<T> requiredType);

    default <T> boolean isConversionNecessary(Object value, Class<T> requiredType){
        return !ClassUtils.isAssignableValue(value, requiredType);
    }
}
