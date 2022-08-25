package com.epam.finalproject.framework.data.sql.mapping;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlValueTranslator {

    Object extract(ResultSet resultSet, SqlFieldDefinition sqlFieldDefinition,String name) throws Exception;

    void write(PreparedStatement statement, SqlFieldDefinition sqlFieldDefinition, int index, Object object) throws Exception;

    boolean supports(SqlFieldDefinition sqlFieldDefinition);

}
