package com.epam.finalproject.framework.data.sql.mapping;

public interface SqlFieldDefinition {

    Class<?> getClazz();

    String getFieldName();

    Class<?> getFieldClass();

    String getColumnName();
}
