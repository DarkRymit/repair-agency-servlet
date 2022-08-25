package com.epam.finalproject.framework.data.sql.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SqlAliasTableNaming {
    String tableName;

    Map<String,SqlAliasTableNaming> fieldTables = new HashMap<>();

    public SqlAliasTableNaming(String tableName) {
        this.tableName = tableName;
    }

    public SqlAliasTableNaming(String tableName,
            Map<String, SqlAliasTableNaming> fieldTables) {
        this.tableName = tableName;
        this.fieldTables = fieldTables;
    }

    public SqlAliasTableNaming() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SqlAliasTableNaming naming = (SqlAliasTableNaming) o;
        return Objects.equals(tableName, naming.tableName) && Objects.equals(fieldTables,
                naming.fieldTables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, fieldTables);
    }

    @Override
    public String toString() {
        return "SqlAliasTableNaming{" +
                "tableName='" + tableName + '\'' +
                ", fieldTables=" + fieldTables +
                '}';
    }

    public String getTableName() {
        return this.tableName;
    }

    public Map<String, SqlAliasTableNaming> getFieldTables() {
        return this.fieldTables;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setFieldTables(Map<String, SqlAliasTableNaming> fieldTables) {
        this.fieldTables = fieldTables;
    }
}
