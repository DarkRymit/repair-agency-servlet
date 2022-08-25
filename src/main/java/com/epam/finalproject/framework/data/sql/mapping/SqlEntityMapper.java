package com.epam.finalproject.framework.data.sql.mapping;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Beans;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.mapping.util.SqlUtil;
import com.epam.finalproject.framework.util.StringUtils;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


@Component
public class SqlEntityMapper {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SqlEntityMapper.class);
    AnnotationSqlDefinitionReader reader;

    List<SqlValueTranslator> extractors;

    @Autowire
    public SqlEntityMapper(AnnotationSqlDefinitionReader reader, @Beans(SqlValueTranslator.class) List<SqlValueTranslator> extractors) {
        this.reader = reader;
        this.extractors = extractors;
    }

    public <T> T map(ResultSet resultSet, Class<T> target) {
        return map(resultSet, target, getInstance(target));
    }

    public <T> T map(ResultSet resultSet, Class<T> target, T object) {
        return map(resultSet, reader.getDefinition(target), null, object);
    }

    public <T> T map(ResultSet resultSet, SqlEntityDefinition<T> definition) {
        return map(resultSet, definition, getInstance(definition.getClazz()));
    }

    public <T> T map(ResultSet resultSet, SqlEntityDefinition<T> definition, T object) {
        return map(resultSet, definition, null, object);
    }


    public <T> T mapAs(ResultSet resultSet, Class<T> target, String tableNaming) {
        return map(resultSet, reader.getDefinition(target), new SqlAliasTableNaming(tableNaming), getInstance(target));
    }

    public <T> T mapAs(ResultSet resultSet, Class<T> target, String tableNaming, T object) {
        return map(resultSet, reader.getDefinition(target), new SqlAliasTableNaming(tableNaming), object);
    }

    public <T> T mapAs(ResultSet resultSet, SqlEntityDefinition<T> entityDefinition, String tableNaming) {
        return map(resultSet, entityDefinition, new SqlAliasTableNaming(tableNaming), getInstance(entityDefinition.getClazz()));
    }

    public <T> T mapAs(ResultSet resultSet, SqlEntityDefinition<T> entityDefinition, String tableNaming, T object) {
        return map(resultSet, entityDefinition, new SqlAliasTableNaming(tableNaming), object);
    }


    public <T> T map(ResultSet resultSet, SqlEntityDefinition<T> entityDefinition, SqlAliasTableNaming naming) {
        return map(resultSet, entityDefinition, naming, getInstance(entityDefinition.getClazz()));
    }

    public <T> T map(ResultSet resultSet, SqlEntityDefinition<T> entityDefinition, SqlAliasTableNaming naming, T object) {
        if (naming != null) {
            String tableName = getTableName(naming, entityDefinition);
            entityDefinition.getFieldDefinitions().stream().filter(this::filterReference).forEach(definition -> {
                String column = definition.getColumnName();
                writeField(object, resultSet, definition, tableName, column);
            });
        } else {
            entityDefinition.getFieldDefinitions()
                    .stream()
                    .filter(this::filterReference)
                    .forEach(definition -> writeField(object, resultSet, definition, entityDefinition.getTableName(), definition.getColumnName()));
        }
        return object;
    }

    private boolean filterReference(SqlFieldDefinition definition) {
        if (definition instanceof ReferenceIdFieldDefinition) {
            return !((ReferenceIdFieldDefinition) definition).isEntity();
        }
        return true;
    }

    public <T> int setValues(PreparedStatement preparedStatement, SqlEntityDefinition<T> entityDefinition, int startIndex, T object) {
        int currentIndex = startIndex;
        for (SqlFieldDefinition definition : entityDefinition.getFieldDefinitions()) {
            if (!notAutoSet(definition)) {
                try {
                    String getterName = "get" + StringUtils.fromUpper(definition.getFieldName());
                    Method getterMethod = definition.getClazz().getDeclaredMethod(getterName);
                    Object value = getterMethod.invoke(object);
                    writeToPreparedStatement(preparedStatement, definition, currentIndex, value);
                    currentIndex++;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return currentIndex;
    }


    private boolean notAutoSet(SqlFieldDefinition definition) {
        return (definition instanceof IdFieldDefinition) || (definition instanceof MapsIdFieldDefinition && ((MapsIdFieldDefinition) definition).isSameAsIdName());
    }

    public <T> int setValues(PreparedStatement preparedStatement, Class<T> entityClass, int startIndex, T object) {
        return setValues(preparedStatement, reader.getDefinition(entityClass), startIndex, object);
    }

    private <T> T getInstance(Class<T> target) {
        try {
            return target.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }


    private String getTableName(SqlAliasTableNaming naming, SqlEntityDefinition<?> entityDefinition) {
        String tableName = entityDefinition.getTableName();
        if (StringUtils.hasText(naming.getTableName())) {
            tableName = naming.getTableName();
        }
        return tableName;
    }

    private <T> void writeField(T object, ResultSet resultSet, SqlFieldDefinition definition, String tableName, String columnName) {
        try {
            String setterName = "set" + StringUtils.fromUpper(definition.getFieldName());
            Method setterMethod = definition.getClazz().getDeclaredMethod(setterName, definition.getFieldClass());
            Object value = readFromResultSet(resultSet, definition, tableName, columnName);
            setterMethod.invoke(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object readFromResultSet(ResultSet resultSet, SqlFieldDefinition definition, String tableName, String columnName) throws Exception {
        log.trace("Start read value to definition {}", definition);
        SqlValueTranslator extractor = extractors.stream()
                .filter(sqlValueExtractor -> sqlValueExtractor.supports(definition))
                .findFirst()
                .orElseThrow();
        return extractor.extract(resultSet, definition, SqlUtil.getFullColumnName(tableName, columnName));
    }

    private void writeToPreparedStatement(PreparedStatement statement, SqlFieldDefinition definition, int index, Object object) throws Exception {
        log.trace("Start write value to definition {}", definition);
        SqlValueTranslator translator = extractors.stream()
                .filter(sqlValueExtractor -> sqlValueExtractor.supports(definition))
                .findFirst()
                .orElseThrow();
        translator.write(statement, definition, index, object);
    }

}
