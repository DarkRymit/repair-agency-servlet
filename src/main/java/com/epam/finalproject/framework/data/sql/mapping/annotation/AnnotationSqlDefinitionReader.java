package com.epam.finalproject.framework.data.sql.mapping.annotation;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.sql.mapping.*;
import com.epam.finalproject.framework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AnnotationSqlDefinitionReader {

    private SqlFieldDefinition getDefinition(Field field) {
        SqlColumn column = field.getAnnotation(SqlColumn.class);
        SqlId id = field.getAnnotation(SqlId.class);
        SqlReferenceId referenceId = field.getAnnotation(SqlReferenceId.class);
        SqlMapsId mapsId = field.getAnnotation(SqlMapsId.class);
        SqlFieldDefinition definition;
        if (column != null) {
            if (id != null) {
                definition = getIdFieldDefinition(field, column);
            } else if (referenceId != null) {
                if (mapsId!=null){
                    definition = getMapsIdFieldDefinition(field, column, referenceId,mapsId);
                }else {
                    definition = getReferenceIdFieldDefinition(field, column, referenceId);
                }
            } else {
                definition = getSimpleFieldDefinition(field, column);
            }
            return definition;
        }
        return null;
    }

    private SqlFieldDefinition getMapsIdFieldDefinition(Field field, SqlColumn column, SqlReferenceId reference,SqlMapsId mapsId) {
        MapsIdFieldDefinition definition = new MapsIdFieldDefinition();
        definition.setEntity(reference.isEntity());
        definition.setClazz(field.getDeclaringClass());
        definition.setFieldName(field.getName());
        definition.setFieldClass(field.getType());
        definition.setColumnName(column.value());
        definition.setSameAsIdName(mapsId.sameAsIdName());
        return definition;
    }

    private SimpleFieldDefinition getSimpleFieldDefinition(Field field, SqlColumn column) {
        SimpleFieldDefinition definition = new SimpleFieldDefinition();
        definition.setClazz(field.getDeclaringClass());
        definition.setFieldName(field.getName());
        definition.setFieldClass(field.getType());
        definition.setColumnName(column.value());
        return definition;
    }

    private ReferenceIdFieldDefinition getReferenceIdFieldDefinition(Field field, SqlColumn column, SqlReferenceId reference) {
        ReferenceIdFieldDefinition definition = new ReferenceIdFieldDefinition();
        definition.setEntity(reference.isEntity());
        definition.setClazz(field.getDeclaringClass());
        definition.setFieldName(field.getName());
        definition.setFieldClass(field.getType());
        definition.setColumnName(column.value());
        return definition;
    }

    private IdFieldDefinition getIdFieldDefinition(Field field, SqlColumn column) {
        IdFieldDefinition definition = new IdFieldDefinition();
        definition.setClazz(field.getDeclaringClass());
        definition.setFieldName(field.getName());
        definition.setFieldClass(field.getType());
        if (StringUtils.hasText(column.value())) {
            definition.setColumnName(column.value());
        } else {
            definition.setColumnName("id");
        }
        try {
            definition.setIdGetter(field.getDeclaringClass().getDeclaredMethod("get" + StringUtils.fromUpper(definition.getFieldName())));
            definition.setIdSetter(field.getDeclaringClass().getDeclaredMethod("set" + StringUtils.fromUpper(definition.getFieldName()), Long.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("Exception during read field %s",field),e);
        }
        return definition;
    }

    public List<SqlFieldDefinition> getDefinitions(Class<?> target) {
        return Arrays.stream(target.getDeclaredFields())
                .map(this::getDefinition)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public <T> SqlEntityDefinition<T> getDefinition(Class<T> target) {
        SqlEntityDefinition<T> definition = new SqlEntityDefinition<>();
        definition.setClazz(target);
        SqlTable table = target.getAnnotation(SqlTable.class);
        definition.setClazz(target);
        definition.setTableName(table.value());
        definition.setFieldDefinitions(getDefinitions(target));
        return definition;
    }
}
