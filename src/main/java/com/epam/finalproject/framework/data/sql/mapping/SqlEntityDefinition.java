package com.epam.finalproject.framework.data.sql.mapping;

import java.util.List;
import java.util.Objects;

public class SqlEntityDefinition<T> {

    private Class<T> clazz;

    private String tableName;

    private List<SqlFieldDefinition> fieldDefinitions;

    public SqlEntityDefinition() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SqlEntityDefinition<?> that = (SqlEntityDefinition<?>) o;
        return Objects.equals(clazz, that.clazz) && Objects.equals(tableName,
                that.tableName) && Objects.equals(fieldDefinitions, that.fieldDefinitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, tableName, fieldDefinitions);
    }

    @Override
    public String toString() {
        return "SqlEntityDefinition{" +
                "clazz=" + clazz +
                ", tableName='" + tableName + '\'' +
                ", fieldDefinitions=" + fieldDefinitions +
                '}';
    }

    public Class<T> getClazz() {
        return this.clazz;
    }

    public String getTableName() {
        return this.tableName;
    }

    public List<SqlFieldDefinition> getFieldDefinitions() {
        return this.fieldDefinitions;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setFieldDefinitions(List<SqlFieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = fieldDefinitions;
    }
}
