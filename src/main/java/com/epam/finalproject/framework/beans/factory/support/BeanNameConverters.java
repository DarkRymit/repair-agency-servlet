package com.epam.finalproject.framework.beans.factory.support;

import com.epam.finalproject.framework.util.StringUtils;

public abstract  class  BeanNameConverters {

    private BeanNameConverters() {
    }

    static String normalizeName(String name) {
        String result = name.strip();
        if (result.isBlank()) {
            throw new IllegalArgumentException("Conversion failed for name" + name);
        }
        result = StringUtils.fromLower(result);
        return result;
    }
    static String fieldNameToSetterName(String name) {
        String result = name.strip();
        if (result.isBlank()) {
            throw new IllegalArgumentException("Conversion failed for name" + name);
        }
        result = "set" + StringUtils.fromUpper(result);
        return result;
    }
    static String setterNameToFieldName(String name) {
        String result = name.strip();
        if (result.isBlank()) {
            throw new IllegalArgumentException("Conversion failed for name" + name);
        }
        result = result.substring(2, 3).toLowerCase() + result.substring(3);
        return result;
    }
}
