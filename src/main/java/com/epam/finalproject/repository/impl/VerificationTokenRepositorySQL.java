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

import java.util.Optional;

import static com.epam.finalproject.repository.impl.SqlAliasConstants.USER_ALIAS;

@Component
public class VerificationTokenRepositorySQL extends SqlAnnotationDrivenRepository<VerificationToken> implements VerificationTokenRepository {

    public static final String SELECT_EAGER_FORMAT = "SELECT %s FROM verification_tokens as t " +
            "left join users as u on t.user_id = u.id ";
    public static final String SELECT_EAGER = String.format(SELECT_EAGER_FORMAT,
            " t.id as t_id , t.expiry_date as t_expiry_date , t.token as t_token , t.user_id as t_user_id " + ","
                    + USER_ALIAS);

    @Autowire
    public VerificationTokenRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper, SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, VerificationToken.class);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return template.query(SELECT_EAGER +  " where t.token = ?", ps -> ps.setString(1, token), wrapToOptional((rs, rowNum) -> {
            VerificationToken verificationToken = entityMapper.mapAs(rs, entitySqlDefinition, "t");
            verificationToken.setUser(entityMapper.mapAs(rs, User.class, "u"));
            return verificationToken;
        }));
    }

}
