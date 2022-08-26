package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.repository.RoleRepository;

import java.util.Optional;

import static com.epam.finalproject.repository.impl.SqlAliasConstants.ROLES_ALIAS;

@Component
public class RoleRepositorySQL extends SqlAnnotationDrivenRepository<Role> implements RoleRepository {

    @Autowire
    public RoleRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper, SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, Role.class);
    }

    @Override
    public Optional<Role> findByName(RoleEnum name) {
        return template.query("SELECT "+ROLES_ALIAS+" FROM roles as r where r.name = ?", ps -> ps.setString(1, name.name()), wrapToOptional((rs, rowNum) -> entityMapper.mapAs(rs, entitySqlDefinition, "r")));
    }

    @Override
    public int addRoleForUser(Role role, User user) {
        return template.update("INSERT INTO user_has_role (user_id,role_id) values (?,?)", ps -> {
            ps.setLong(1,user.getId());
            ps.setLong(2,role.getId());
        });
    }

    @Override
    public int deleteRoleForUser(Role role, User user) {
        return template.update("DELETE  FROM user_has_role as uhr where uhr.user_id = ? and uhr.role_id = ?", ps -> {
            ps.setLong(1,user.getId());
            ps.setLong(2,role.getId());
        });
    }
}
