package com.epam.finalproject.framework.beans.factory.config;

public class BeanReference {
    private final String name;

    private final Class<?> clazz;

    public BeanReference(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public BeanReference(Class<?> clazz) {
        this.clazz = clazz;
        this.name=null;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
