package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.repository.PasswordResetTokenRepository;

import java.util.Date;
import java.util.Optional;
@Component
public class PasswordResetTokenRepositorySQL extends SqlAnnotationDrivenRepository<PasswordResetToken> implements PasswordResetTokenRepository {

    @Autowire
    public PasswordResetTokenRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper, SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, PasswordResetToken.class);
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public void deleteAllExpiredSince(Date now) {

    }
}
