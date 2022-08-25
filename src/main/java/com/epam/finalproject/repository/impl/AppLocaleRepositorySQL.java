package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.AppLocale;
import com.epam.finalproject.repository.AppLocaleRepository;

import java.util.Optional;
@Component
public class AppLocaleRepositorySQL extends SqlAnnotationDrivenRepository<AppLocale> implements AppLocaleRepository {
    @Autowire
    public AppLocaleRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper, SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, AppLocale.class);
    }

    @Override
    public Optional<AppLocale> findByLang(String lang) {
        return template.query(queryGenerator.selectQuery(entityClass) + " where lang = ?", pss -> pss.setString(1, lang), wrapToOptional((rs, rowNum) -> entityMapper.map(rs, entityClass)));
    }
}
