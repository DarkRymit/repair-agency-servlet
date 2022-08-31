package com.epam.finalproject.framework.beans.factory;

import com.epam.finalproject.framework.util.ClassUtils;

/**
 * The interface Type converter.
 */
public interface TypeConverter {
    /**
     * Convert if necessary.
     *
     * @param value        the value
     * @param requiredType the required type
     * @return the t
     * @throws TypeMismatchException if conversion necessary but can't be performed
     */
    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;

    /**
     * Is conversion available boolean.
     *
     * @param value        the value
     * @param requiredType the required type
     * @return {@code true} if conversion available or {@code false} otherwise
     */
    <T> boolean isConversionAvailable(Object value, Class<T> requiredType);

    /**
     * Is conversion necessary boolean.
     *
     * @param value        the value
     * @param requiredType the required type
     * @return {@code true} if conversion necessary or {@code false} otherwise
     */
    default <T> boolean isConversionNecessary(Object value, Class<T> requiredType){
        return !ClassUtils.isAssignableValue(value, requiredType);
    }
}
