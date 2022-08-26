package com.epam.finalproject.framework.data.sql.mapping.util;

public interface SqlUtil {

    static String getFullColumnName(String tableName, String columnName) {
        return tableName + "." + columnName;
    }

    static String getAliasColumnName(String tableName, String columnName) {
        return tableName + "_" + columnName;
    }
}
