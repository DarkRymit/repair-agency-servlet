package com.epam.finalproject.framework.data.sql.mapping.translators;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.sql.mapping.SqlFieldDefinition;
import com.epam.finalproject.framework.data.sql.mapping.SqlValueTranslator;
import com.epam.finalproject.framework.util.ClassUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StringTranslator implements SqlValueTranslator {

    @Override
    public Object extract(ResultSet resultSet, SqlFieldDefinition sqlFieldDefinition, String name) throws SQLException {
        return resultSet.getString(name);

    }

    @Override
    public void write(PreparedStatement statement, SqlFieldDefinition sqlFieldDefinition, int index, Object object) throws SQLException {
        statement.setString(index,(String)object);
    }

    @Override
    public boolean supports(SqlFieldDefinition sqlFieldDefinition) {
        return ClassUtils.isAssignable(sqlFieldDefinition.getFieldClass(), String.class);
    }
}
