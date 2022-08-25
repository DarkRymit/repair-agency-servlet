package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.repository.AppCurrencyRepository;

import java.util.Optional;

@Component
public class AppCurrencyRepositorySQL extends SqlAnnotationDrivenRepository<AppCurrency> implements AppCurrencyRepository {

    @Autowire
    public AppCurrencyRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper, SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, AppCurrency.class);
    }

    @Override
    public Optional<AppCurrency> findByCode(String code) {
        return template.query(queryGenerator.selectQuery(entityClass) + " where code = ?", pss -> pss.setString(1, code), wrapToOptional((rs, rowNum) -> entityMapper.map(rs, entityClass)));
    }
}
