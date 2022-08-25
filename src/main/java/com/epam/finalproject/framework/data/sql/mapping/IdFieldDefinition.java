package com.epam.finalproject.framework.data.sql.mapping;


import java.lang.reflect.Method;

public class IdFieldDefinition extends SimpleFieldDefinition {
    private Method idSetter;
    private Method idGetter;

    public IdFieldDefinition() {
    }

    public IdFieldDefinition(Class<?> clazz, String fieldName, Class<?> fieldClass, String columnName, String columnDefinition, Method idSetter, Method idGetter) {
        super(clazz, fieldName, fieldClass, columnName, columnDefinition);
        this.idSetter = idSetter;
        this.idGetter = idGetter;
    }

    public Method getIdSetter() {
        return idSetter;
    }

    public void setIdSetter(Method idSetter) {
        this.idSetter = idSetter;
    }

    public Method getIdGetter() {
        return idGetter;
    }

    public void setIdGetter(Method idGetter) {
        this.idGetter = idGetter;
    }
}
