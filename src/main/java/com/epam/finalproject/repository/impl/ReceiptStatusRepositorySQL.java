package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.ReceiptStatus;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.repository.ReceiptStatusRepository;

import java.util.Optional;
@Component
public class ReceiptStatusRepositorySQL extends SqlAnnotationDrivenRepository<ReceiptStatus> implements ReceiptStatusRepository {

    @Autowire
    public ReceiptStatusRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper, SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template,definitionReader,entityMapper,queryGenerator,ReceiptStatus.class);
    }

    @Override
    public Optional<ReceiptStatus> findByName(ReceiptStatusEnum name) {
        return template.query(queryGenerator.selectQuery(entityClass) + " WHERE name = ?", pss -> pss.setString(1, name.name()), wrapToOptional((rs, rowNum) -> entityMapper.map(rs, entityClass)));
    }
}
