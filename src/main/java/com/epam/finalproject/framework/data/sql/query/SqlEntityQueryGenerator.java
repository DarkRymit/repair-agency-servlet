package com.epam.finalproject.framework.data.sql.query;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.framework.data.sql.mapping.*;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.mapping.util.SqlUtil;
import com.epam.finalproject.framework.util.CustomCollectionsUtil;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.epam.finalproject.framework.data.sql.mapping.util.SqlUtil.getAliasColumnName;
import static com.epam.finalproject.framework.data.sql.mapping.util.SqlUtil.getFullColumnName;

@Component
public class SqlEntityQueryGenerator {

    public static final String SELECT = "SELECT";
    public static final String GENERATED_QUERY = "Generated query {}";
    public static final String WHERE = "WHERE";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SqlEntityQueryGenerator.class);
    AnnotationSqlDefinitionReader reader;

    @Autowire
    public SqlEntityQueryGenerator(AnnotationSqlDefinitionReader reader) {
        this.reader = reader;
    }

    public <T> String selectQuery(Class<T> target) {
        return selectQuery(reader.getDefinition(target));
    }

    public <T> String selectQuery(SqlEntityDefinition<T> entityDefinition) {
        StringBuilder query = new StringBuilder();
        List<SqlFieldDefinition> definitionsToWrite = getDefinitionsToWrite(entityDefinition);
        query.append(SELECT)
                .append(" ")
                .append(definitionsToWrite.stream()
                        .map(SqlFieldDefinition::getColumnName)
                        .collect(Collectors.joining(",")))
                .append(" ")
                .append("FROM")
                .append(" ")
                .append(entityDefinition.getTableName())
                .append(" ");
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }

    public <T> String selectQuery(Class<T> target, SqlAliasTableNaming naming) {
        return selectQuery(reader.getDefinition(target), naming);
    }

    public <T> String selectQuery(SqlEntityDefinition<T> entityDefinition, SqlAliasTableNaming naming) {
        StringBuilder query = new StringBuilder();
        List<SqlFieldDefinition> definitionsToWrite = getDefinitionsToWrite(entityDefinition);
        query.append(SELECT)
                .append(" ")
                .append(definitionsToWrite.stream()
                        .map(definition -> getFullColumnName(naming.getTableName(), definition.getColumnName()) + " As " + getAliasColumnName(naming.getTableName(), definition.getColumnName()))
                        .collect(Collectors.joining(",")))
                .append(" ")
                .append("FROM")
                .append(" ")
                .append(entityDefinition.getTableName())
                .append(" as ")
                .append(naming.getTableName())
                .append(" ");
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }

    public <T> String updateByIdQuery(SqlEntityDefinition<T> entityDefinition) {
        StringBuilder query = new StringBuilder();
        List<SqlFieldDefinition> definitionsToWrite = getDefinitionsToWrite(entityDefinition);
        query.append("UPDATE")
                .append(" ")
                .append(entityDefinition.getTableName())
                .append(" ")
                .append("SET")
                .append(" ")
                .append(definitionsToWrite.stream()
                        .map(SqlFieldDefinition::getColumnName)
                        .map(s -> s + " = ? ")
                        .collect(Collectors.joining(",")))
                .append(" ")
                .append(WHERE)
                .append(" ")
                .append(getIdColumn(entityDefinition).getColumnName())
                .append(" ")
                .append("=")
                .append(" ")
                .append("?");
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }

    public <T> String insertQuery(SqlEntityDefinition<T> entityDefinition) {
        StringBuilder query = new StringBuilder();
        List<SqlFieldDefinition> definitionsToWrite = getDefinitionsToWrite(entityDefinition);
        doInsertQuery(entityDefinition,query,definitionsToWrite);
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }
    public <T> String insertQueryNoId(SqlEntityDefinition<T> entityDefinition) {
        StringBuilder query = new StringBuilder();
        List<SqlFieldDefinition> definitionsToWrite = entityDefinition.getFieldDefinitions()
                .stream()
                .filter(definition -> !ignoreOnQuery(definition))
                .filter(definition -> !(definition instanceof IdFieldDefinition))
                .collect(Collectors.toList());
        doInsertQuery(entityDefinition, query, definitionsToWrite);
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }

    private <T> void doInsertQuery(SqlEntityDefinition<T> entityDefinition, StringBuilder query,
            List<SqlFieldDefinition> definitionsToWrite) {
        query.append("INSERT")
                .append(" ")
                .append("INTO")
                .append(" ")
                .append(entityDefinition.getTableName())
                .append(" ")
                .append("(")
                .append(definitionsToWrite.stream()
                        .map(SqlFieldDefinition::getColumnName)
                        .collect(Collectors.joining(",")))
                .append(")")
                .append(" ")
                .append("VALUES")
                .append(" ")
                .append("(")
                .append(IntStream.rangeClosed(1, definitionsToWrite.size())
                        .mapToObj(value -> "?")
                        .collect(Collectors.joining(",")))
                .append(")");
    }

    private <T> List<SqlFieldDefinition> getDefinitionsToWrite(SqlEntityDefinition<T> entityDefinition) {
        return entityDefinition.getFieldDefinitions()
                .stream()
                .filter(definition -> !ignoreOnQuery(definition))
                .collect(Collectors.toList());
    }

    public <T> String selectAllLimitOffset(SqlEntityDefinition<T> entityDefinition) {
        StringBuilder query = new StringBuilder();
        query.append(selectQuery(entityDefinition)).append(" LIMIT ? ").append(" OFFSET ? ");
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }

    public <T> String selectById(SqlEntityDefinition<T> entityDefinition) {
        StringBuilder query = new StringBuilder();
        query.append(selectQuery(entityDefinition))
                .append(WHERE)
                .append(" ")
                .append(getIdColumn(entityDefinition).getColumnName())
                .append(" ")
                .append("=")
                .append(" ")
                .append("?");
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }

    public <T> String deleteById(SqlEntityDefinition<T> entityDefinition) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE")
                .append(" ")
                .append("FROM")
                .append(" ")
                .append(entityDefinition.getTableName())
                .append(" ")
                .append(WHERE)
                .append(" ")
                .append(getIdColumn(entityDefinition).getColumnName())
                .append(" ")
                .append("=")
                .append(" ")
                .append("?");
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }

    public <T> String deleteById(Class<T> target) {
        return deleteById(reader.getDefinition(target));
    }

    public <T> String existById(SqlEntityDefinition<T> entityDefinition) {
        StringBuilder query = new StringBuilder();
        query.append(countQuery(entityDefinition))
                .append(WHERE)
                .append(" ")
                .append(getIdColumn(entityDefinition).getColumnName())
                .append(" ")
                .append("=")
                .append(" ")
                .append("?");
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }

    private <T> SqlFieldDefinition getIdColumn(SqlEntityDefinition<T> entityDefinition) {
        return entityDefinition.getFieldDefinitions()
                .stream()
                .filter(IdFieldDefinition.class::isInstance)
                .collect(CustomCollectionsUtil.toOneOrEmpty())
                .orElseThrow();
    }

    public <T> String order(SqlEntityDefinition<T> entityDefinition,Sort sort,SqlAliasTableNaming tableNaming) {
        if (haveSort(sort)){
            return " ";
        }
        StringBuilder query = new StringBuilder();
        query.append(" ORDER BY ");
        Map<String, SqlFieldDefinition> definitionMap = getDefinitionsMap(entityDefinition);
        List<String> parts = new ArrayList<>();
        for (Sort.Order order : sort.toList()) {
            String part;
            part = getOrderPart(definitionMap, order,order.getProperty(),tableNaming);
            parts.add(part);
        }
        query.append(String.join(",", parts));
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }

    private boolean haveSort(Sort sort) {
        return sort.equals(Sort.unsorted()) || sort.toList().isEmpty();
    }

    public <T> String order(SqlEntityDefinition<T> entityDefinition,Sort sort) {
        if (haveSort(sort)){
            return " ";
        }
        StringBuilder query = new StringBuilder();
        query.append(" ORDER BY ");
        Map<String, SqlFieldDefinition> definitionMap = getDefinitionsMap(entityDefinition);
        List<String> parts = new ArrayList<>();
        for (Sort.Order order : sort.toList()) {
            String part;
            if(order.getProperty().contains(".")){
                throw new IllegalArgumentException(String.format("Not support deep property without tableNaming %s",order.getProperty()));
            }
            String sortColumn = definitionMap.get(order.getProperty()).getColumnName();
            part = order.isIgnoreCase() ? " LOWER(" + sortColumn + ") " : " " + SqlUtil.getFullColumnName(entityDefinition.getTableName(),sortColumn) + " ";
            part = part + (order.isAscending() ? "ASC " : "DESC ");
            parts.add(part);
        }
        query.append(String.join(",", parts));
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }

    private String getOrderPart(Map<String, SqlFieldDefinition> definitionMap, Sort.Order order,String propertyPart , SqlAliasTableNaming tableNaming) {
        String part;
        if(propertyPart.contains(".")){
            String[] strings = propertyPart.split("\\.",2);
            SqlFieldDefinition definition = definitionMap.get(strings[0]);
            if (definition instanceof ReferenceIdFieldDefinition){
                SqlAliasTableNaming naming = tableNaming.getFieldTables().get(definition.getFieldName());
                SqlEntityDefinition<?> subEntityDefinition = reader.getDefinition(definition.getFieldClass());
                part = getOrderPart(getDefinitionsMap(subEntityDefinition),order,strings[1],naming);
            }else {
                throw new IllegalArgumentException(String.format("Cant resolve reference %s", order.getProperty()));
            }
        }else {
            String sortColumn = definitionMap.get(order.getProperty()).getColumnName();
            part = order.isIgnoreCase() ? " LOWER(" +sortColumn+") " : " " + SqlUtil.getFullColumnName(tableNaming.getTableName(),sortColumn) + " ";
            part = part + (order.isAscending() ? "ASC " : "DESC ");
        }
        return part;
    }

    private <T> Map<String, SqlFieldDefinition> getDefinitionsMap(SqlEntityDefinition<T> entityDefinition) {
        return entityDefinition.getFieldDefinitions()
                .stream()
                .collect(Collectors.toMap(SqlFieldDefinition::getFieldName, definition -> definition));
    }

    public <T> String countQuery(SqlEntityDefinition<T> entityDefinition) {
        StringBuilder query = new StringBuilder();
        query.append(SELECT)
                .append(" ")
                .append("COUNT(*)")
                .append(" ")
                .append("FROM")
                .append(" ")
                .append(entityDefinition.getTableName())
                .append(" ");
        log.trace(GENERATED_QUERY, query);
        return query.toString();
    }

    private boolean ignoreOnQuery(SqlFieldDefinition definition) {
        return (definition instanceof MapsIdFieldDefinition && ((MapsIdFieldDefinition) definition).isSameAsIdName());
    }
}
