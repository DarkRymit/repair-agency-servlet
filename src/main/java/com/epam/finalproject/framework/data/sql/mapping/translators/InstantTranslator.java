package com.epam.finalproject.framework.data.sql.mapping.translators;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.sql.mapping.SqlFieldDefinition;
import com.epam.finalproject.framework.data.sql.mapping.SqlValueTranslator;
import com.epam.finalproject.framework.util.ClassUtils;

import java.sql.*;
import java.time.Instant;

@Component
public class InstantTranslator implements SqlValueTranslator {

    @Override
    public Object extract(ResultSet resultSet, SqlFieldDefinition sqlFieldDefinition, String name) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(name);
        if (timestamp!=null){
            return timestamp.toInstant();
        }
        return null;
    }

    @Override
    public void write(PreparedStatement statement, SqlFieldDefinition sqlFieldDefinition, int index, Object object) throws SQLException {
        if (object!=null){
            statement.setTimestamp(index, Timestamp.from((Instant)object));
        }else {
            statement.setNull(index, Types.TIMESTAMP);
        }
    }

    @Override
    public boolean supports(SqlFieldDefinition sqlFieldDefinition) {
        return ClassUtils.isAssignable(sqlFieldDefinition.getFieldClass(), Instant.class);
    }
}
