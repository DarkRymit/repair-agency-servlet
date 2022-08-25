package com.epam.finalproject.framework.beans.factory.config;

import java.util.Objects;

public class ConstructValueHolder {

    private final Class<?> clazz;

    private final Object value;

    public ConstructValueHolder(Class<?> clazz, Object value) {
        this.clazz = clazz;
        this.value = value;
    }


    public Class<?> getClazz() {
        return clazz;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstructValueHolder that = (ConstructValueHolder) o;

        return Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }

    @Override
    public String toString() {
        return "ConstructValueHolder{" +
                ", clazz=" + clazz +
                ", value=" + value +
                '}';
    }
}
