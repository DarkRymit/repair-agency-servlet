package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.repository.VerificationTokenRepository;

import java.util.Date;
import java.util.Optional;

@Component
public class VerificationTokenRepositorySQL extends SqlAnnotationDrivenRepository<VerificationToken> implements VerificationTokenRepository {

    @Autowire
    public VerificationTokenRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper, SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, VerificationToken.class);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return template.query("SELECT t.*,u.* FROM verification_tokens as t LEFT JOIN users as u on t.user_id = u.id  where t.token = ?", ps -> ps.setString(1, token), wrapToOptional((rs, rowNum) -> {
            VerificationToken verificationToken = entityMapper.mapAs(rs, entitySqlDefinition, "t");
            verificationToken.setUser(entityMapper.mapAs(rs, User.class, "u"));
            return verificationToken;
        }));
    }

    @Override
    public void deleteAllExpiredSince(Date now) {

    }
}
