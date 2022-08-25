package com.epam.finalproject.framework.data.sql.mapping;

import java.util.Objects;

public class SimpleFieldDefinition implements SqlFieldDefinition {

    Class<?> clazz;

    String fieldName;

    Class<?> fieldClass;

    String columnName;

    String columnDefinition;

    public SimpleFieldDefinition(Class<?> clazz, String fieldName, Class<?> fieldClass, String columnName,
            String columnDefinition) {
        this.clazz = clazz;
        this.fieldName = fieldName;
        this.fieldClass = fieldClass;
        this.columnName = columnName;
        this.columnDefinition = columnDefinition;
    }

    public SimpleFieldDefinition() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleFieldDefinition that = (SimpleFieldDefinition) o;
        return Objects.equals(clazz, that.clazz) && Objects.equals(fieldName,
                that.fieldName) && Objects.equals(fieldClass, that.fieldClass) && Objects.equals(
                columnName, that.columnName) && Objects.equals(columnDefinition, that.columnDefinition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, fieldName, fieldClass, columnName, columnDefinition);
    }

    @Override
    public String toString() {
        return "SimpleFieldDefinition{" +
                "clazz=" + clazz +
                ", fieldName='" + fieldName + '\'' +
                ", fieldClass=" + fieldClass +
                ", columnName='" + columnName + '\'' +
                ", columnDefinition='" + columnDefinition + '\'' +
                '}';
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public Class<?> getFieldClass() {
        return this.fieldClass;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public String getColumnDefinition() {
        return this.columnDefinition;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldClass(Class<?> fieldClass) {
        this.fieldClass = fieldClass;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }
}
