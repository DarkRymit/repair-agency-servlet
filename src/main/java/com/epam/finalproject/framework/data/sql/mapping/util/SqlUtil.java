package com.epam.finalproject.framework.data.sql.mapping.util;

import com.epam.finalproject.framework.data.sql.mapping.SqlAliasTableNaming;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityDefinition;
import com.epam.finalproject.framework.data.sql.mapping.SqlFieldDefinition;
import com.epam.finalproject.framework.util.StringUtils;

import java.util.stream.Collectors;

public abstract class SqlUtil {

    private SqlUtil() {
    }

    public static String getFullColumnName(String tableName, String columnName) {
        return tableName+"."+columnName;
    }
}
