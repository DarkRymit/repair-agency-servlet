package com.epam.finalproject.framework.data.sql.mapping.translators;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.beans.annotation.Order;
import com.epam.finalproject.framework.data.sql.mapping.*;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.util.ClassUtils;
import com.epam.finalproject.framework.util.CustomCollectionsUtil;
import com.epam.finalproject.framework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Order(Integer.MIN_VALUE)
public class ReferenceIdTranslator implements SqlValueTranslator {

    AnnotationSqlDefinitionReader definitionReader;

    public ReferenceIdTranslator(AnnotationSqlDefinitionReader definitionReader) {
        this.definitionReader = definitionReader;
    }

    @Override
    public Object extract(ResultSet resultSet, SqlFieldDefinition sqlFieldDefinition, String name) throws SQLException {
        if (((ReferenceIdFieldDefinition) sqlFieldDefinition).isEntity()) {
            return null;
        }
        return resultSet.getObject(name);
    }

    @Override
    public void write(PreparedStatement statement, SqlFieldDefinition sqlFieldDefinition, int index, Object object) throws Exception {
        ReferenceIdFieldDefinition idFieldDefinition = (ReferenceIdFieldDefinition) sqlFieldDefinition;
        if (idFieldDefinition.isEntity()) {
            SqlEntityDefinition<?> entityDefinition = definitionReader.getDefinition(idFieldDefinition.getFieldClass());
            SqlFieldDefinition id = entityDefinition.getFieldDefinitions()
                    .stream()
                    .filter(IdFieldDefinition.class::isInstance)
                    .collect(CustomCollectionsUtil.toOneOrEmpty())
                    .orElseThrow();
            Object value = null;
            if (object != null){
                value = entityDefinition.getClazz().getDeclaredMethod("get" + StringUtils.fromUpper(id.getFieldName())).invoke(object);
            }
            statement.setObject(index, value);
        } else {
            statement.setObject(index, object);
        }
    }

    @Override
    public boolean supports(SqlFieldDefinition sqlFieldDefinition) {
        return ClassUtils.isAssignableValue(sqlFieldDefinition, ReferenceIdFieldDefinition.class);
    }
}
