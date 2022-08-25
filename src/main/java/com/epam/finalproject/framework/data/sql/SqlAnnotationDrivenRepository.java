package com.epam.finalproject.framework.data.sql;

import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageImpl;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.jdbc.ResultSetExtractor;
import com.epam.finalproject.framework.data.jdbc.RowMapper;
import com.epam.finalproject.framework.data.jdbc.support.RowMapperResultSetToOptionalExtractor;
import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.framework.data.sql.mapping.IdFieldDefinition;
import com.epam.finalproject.framework.data.sql.mapping.MapsIdFieldDefinition;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityDefinition;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.framework.util.CustomCollectionsUtil;
import com.epam.finalproject.framework.util.StringUtils;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SqlAnnotationDrivenRepository<T> implements PagingAndSortingRepository<T, Long> {

    public static final String COUNT_FROM_FORMAT = "SELECT COUNT(*) FROM ( %s ) as q";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SqlAnnotationDrivenRepository.class);

    protected final JdbcTemplate template;

    protected final SqlEntityMapper entityMapper;

    protected final SqlEntityQueryGenerator queryGenerator;

    protected final AnnotationSqlDefinitionReader definitionReader;

    protected final Class<T> entityClass;

    protected final SqlEntityDefinition<T> entitySqlDefinition;

    protected final IdFieldDefinition entityIdFieldDefinition;


    public SqlAnnotationDrivenRepository(JdbcTemplate template, AnnotationSqlDefinitionReader definitionReader, SqlEntityMapper entityMapper, SqlEntityQueryGenerator queryGenerator, Class<T> target) {
        this.template = template;
        this.definitionReader = definitionReader;
        this.entityMapper = entityMapper;
        this.queryGenerator = queryGenerator;
        this.entityClass = target;
        this.entitySqlDefinition = definitionReader.getDefinition(target);
        this.entityIdFieldDefinition = getIdColumn(entitySqlDefinition);
    }

    @Override
    public T save(T entity) {
        return save(entitySqlDefinition,entity);
    }

    protected <S> S save(SqlEntityDefinition<S> definition,S entity) {
        try {
            S result;
            IdFieldDefinition idFieldDefinition = getIdColumn(entitySqlDefinition);
            Method getId = idFieldDefinition.getIdGetter();
            if (getId.invoke(entity) == null) {
                result = onInsert(definition,entity, idFieldDefinition);
            } else {
                result = onUpdate(definition,entity, idFieldDefinition);
            }
            return result;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    protected <S> S onInsert(SqlEntityDefinition<S> definition,S entity, IdFieldDefinition idFieldDefinition) throws ReflectiveOperationException {
        List<Map<String, Object>> keys = new ArrayList<>();
        Long targetId = getTargetId(definition, entity);
        Long newId = targetId;
        template.update(queryGenerator.insertQuery(definition), pss -> {
            pss.setObject(1, targetId);
            entityMapper.setValues(pss, definition, 2, entity);
        }, keys);
        if (newId == null) {
            Object id = keys.get(0).get("GENERATED_KEY");
            newId = ((BigInteger) id).longValue();
        }
        idFieldDefinition.getIdSetter().invoke(entity, newId);
        return entity;
    }

    protected <S> S onUpdate(SqlEntityDefinition<S> definition,S entity,IdFieldDefinition idFieldDefinition) throws ReflectiveOperationException {
        Long id = getTargetId(definition, entity);
        template.update(queryGenerator.updateByIdQuery(definition), pss -> {
            pss.setLong(1, id);
            int end = entityMapper.setValues(pss, definition, 2, entity);
            pss.setLong(end, id);
        });
        idFieldDefinition.getIdSetter().invoke(entity, id);
        return entity;
    }


    @Override
    public Optional<T> findById(Long aLong) {
        return template.query(queryGenerator.selectById(entitySqlDefinition), pss -> pss.setLong(1, aLong), wrapToOptional((rs, rowNum) -> entityMapper.map(rs, entityClass)));
    }

    @Override
    public boolean existsById(Long aLong) {
        return template.query(queryGenerator.existById(entitySqlDefinition), pss -> pss.setLong(1, aLong), (rs -> {
            if (rs.next()) {
                return rs.getLong("count(*)") > 0;
            } else {
                throw new RuntimeException(String.format("Can't exist for entityDefinition %s", entitySqlDefinition));
            }
        }));
    }

    @Override
    public List<T> findAll() {
        return template.query(queryGenerator.selectQuery(entitySqlDefinition), (rs, rowNum) -> entityMapper.map(rs, entityClass));
    }


    @Override
    public long count() {
        return template.query(queryGenerator.countQuery(entitySqlDefinition), (rs -> {
            if (rs.next()) {
                return rs.getLong("count(*)");
            } else {
                throw new RuntimeException(String.format("Can't count for entityDefinition %s", entitySqlDefinition));
            }
        }));
    }

    @Override
    public void deleteById(Long aLong) {
        template.update(queryGenerator.deleteById(entitySqlDefinition), ps -> ps.setLong(1, aLong));
    }

    @Override
    public void delete(T entity) {
        try {
            deleteById((Long) entityIdFieldDefinition.getIdGetter().invoke(entity));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<T> findAll(Sort sort) {
        return template.query(queryGenerator.selectQuery(entitySqlDefinition) + queryGenerator.order(entitySqlDefinition, sort), (rs, rowNum) -> entityMapper.map(rs, entityClass));
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        List<T> content = template.query(queryGenerator.selectQuery(entitySqlDefinition) + queryGenerator.order(entitySqlDefinition, pageable.getSort()) + " LIMIT ? , ?", pss -> {
            pss.setLong(1, pageable.getOffset());
            pss.setLong(2, pageable.getPageSize());
        }, (rs, rowNum) -> entityMapper.map(rs, entityClass));
        long total = count();
        return new PageImpl<>(content, pageable, total);
    }

    protected <U> ResultSetExtractor<Optional<U>> wrapToOptional(RowMapper<U> mapper) {
        return new RowMapperResultSetToOptionalExtractor<>(mapper);
    }

    protected <U> IdFieldDefinition getIdColumn(SqlEntityDefinition<U> entityDefinition) {
        return entityDefinition.getFieldDefinitions()
                .stream()
                .filter(IdFieldDefinition.class::isInstance)
                .map(IdFieldDefinition.class::cast)
                .collect(CustomCollectionsUtil.toOneOrEmpty())
                .orElseThrow();
    }

    protected <U> MapsIdFieldDefinition getMapsIdColumn(SqlEntityDefinition<U> entityDefinition) {
        return entityDefinition.getFieldDefinitions()
                .stream()
                .filter(MapsIdFieldDefinition.class::isInstance)
                .map(MapsIdFieldDefinition.class::cast)
                .collect(CustomCollectionsUtil.toOneOrEmpty())
                .orElse(null);
    }

    protected <U> Long getTargetId(SqlEntityDefinition<U> definition, U entity) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Long targetId = (Long) getIdColumn(definition).getIdGetter().invoke(entity);
        MapsIdFieldDefinition mapsId = getMapsIdColumn(definition);
        if (mapsId != null) {
            Object fieldValue = mapsId.getClazz()
                    .getDeclaredMethod("get" + StringUtils.fromUpper(mapsId.getFieldName()))
                    .invoke(entity);
            if (mapsId.isEntity()) {
                SqlEntityDefinition<?> mapsDefinition = definitionReader.getDefinition(mapsId.getFieldClass());
                targetId = (Long) getIdColumn(mapsDefinition).getIdGetter().invoke(fieldValue);
            } else {
                targetId = (Long) fieldValue;
            }
        }
        return targetId;
    }

    protected int setDynamicPart(List<Object> dynamicPartValue, PreparedStatement ps) throws SQLException {
        int currentPosition = 1;
        for (Object obj : dynamicPartValue) {
            if (obj instanceof Long){
                ps.setLong(currentPosition,(Long) obj);
            }else {
                ps.setString(currentPosition, (String) obj);
            }
            currentPosition++;
        }
        return currentPosition;
    }

    protected long getCount(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong("COUNT(*)");
        } else {
            throw new RuntimeException();
        }
    }
}
