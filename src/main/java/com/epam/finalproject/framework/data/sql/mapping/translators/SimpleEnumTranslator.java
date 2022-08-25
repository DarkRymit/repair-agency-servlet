package com.epam.finalproject.framework.data.sql.mapping.translators;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.sql.mapping.SqlFieldDefinition;
import com.epam.finalproject.framework.data.sql.mapping.SqlValueTranslator;
import com.epam.finalproject.framework.util.ClassUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Component
public class SimpleEnumTranslator implements SqlValueTranslator {

    @Override
    public Object extract(ResultSet resultSet, SqlFieldDefinition sqlFieldDefinition, String name) throws SQLException {
        String value = resultSet.getString(name);
        return Arrays.stream(sqlFieldDefinition.getFieldClass().getEnumConstants())
                .map(Enum.class::cast)
                .filter(anEnum -> anEnum.name().equals(value))
                .findFirst()
                .orElse(null);

    }

    @Override
    public void write(PreparedStatement statement, SqlFieldDefinition sqlFieldDefinition, int index, Object object) throws SQLException {
        statement.setString(index, ((Enum) object).name());
    }

    @Override
    public boolean supports(SqlFieldDefinition sqlFieldDefinition) {
        return ClassUtils.isAssignable(sqlFieldDefinition.getFieldClass(), Enum.class);
    }
}
