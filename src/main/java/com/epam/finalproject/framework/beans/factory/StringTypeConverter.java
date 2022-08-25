package com.epam.finalproject.framework.beans.factory;

import com.epam.finalproject.framework.beans.annotation.Component;

import java.beans.PropertyEditor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class StringTypeConverter implements TypeConverter {
    private static final Map<Class<?>, Function<String,?>> convertMap;

    static  {
        convertMap = new HashMap<>();

        convertMap.put(boolean.class, Boolean::valueOf);
        convertMap.put(Boolean.class, Boolean::valueOf);
        convertMap.put(byte.class, Byte::valueOf);
        convertMap.put(Byte.class, Byte::valueOf);
        convertMap.put(short.class, Short::valueOf);
        convertMap.put(Short.class, Short::valueOf);
        convertMap.put(int.class, Integer::valueOf);
        convertMap.put(Integer.class, Integer::valueOf);
        convertMap.put(long.class, Long::valueOf);
        convertMap.put(Long.class, Long::valueOf);
        convertMap.put(float.class, Float::valueOf);
        convertMap.put(Float.class, Float::valueOf);
        convertMap.put(double.class, Double::valueOf);
        convertMap.put(Double.class, Double::valueOf);
        convertMap.put(BigDecimal.class, BigDecimal::new);
        convertMap.put(BigInteger.class, BigInteger::new);
    }
    public StringTypeConverter() {
    }

    public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {

        if (!isConversionNecessary(value, requiredType)) {
            return (T) value;
        } else {
            if (value instanceof String) {
                Function<String,?> function = convertMap.get(requiredType);
                Object result;
                try {
                    result = function.apply((String) value);
                } catch (IllegalArgumentException e) {
                    throw new TypeMismatchException(value, requiredType);
                }
                return (T) result;
            } else {
                throw new TypeMismatchException(value,requiredType);
            }
        }
    }

    @Override
    public <T> boolean isConversionAvailable(Object value, Class<T> requiredType) {
        return convertMap.containsKey(requiredType);
    }

}
