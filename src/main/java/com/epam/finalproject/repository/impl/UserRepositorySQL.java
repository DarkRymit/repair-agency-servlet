package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageImpl;
import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.IdFieldDefinition;
import com.epam.finalproject.framework.data.sql.mapping.SqlAliasTableNaming;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.framework.security.Authentication;
import com.epam.finalproject.framework.security.GrantedAuthority;
import com.epam.finalproject.framework.security.support.SecurityContext;
import com.epam.finalproject.framework.security.support.SecurityContextHolder;
import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.model.search.MasterSearch;
import com.epam.finalproject.model.search.UserSearch;
import com.epam.finalproject.repository.UserRepository;

import java.sql.ResultSet;
import java.time.Instant;
import java.util.*;

import static com.epam.finalproject.repository.impl.SqlAliasConstants.*;

@Component
public class UserRepositorySQL extends SqlAnnotationDrivenRepository<User> implements UserRepository {


    public static final String SELECT_EAGER_FORMAT = "SELECT %s FROM users as u left join user_has_role as uhr on u.id = uhr.user_id left join roles as r on r.id = uhr.role_id left join wallets as w on u.id = w.user_id left join app_currencies as ac on w.currency_id = ac.id ";

    public static final String SELECT_EAGER = String.format(SELECT_EAGER_FORMAT, USER_ALIAS + "," + ROLES_ALIAS + "," + WALLETS_ALIAS + "," + APP_CURRENCIES_ALIAS);
    public static final String SELECT_EAGER_ONE_TO_ONE_FORMAT = "SELECT %s FROM users as u ";

    public static final String SELECT_EAGER_ONE_TO_ONE = String.format(SELECT_EAGER_ONE_TO_ONE_FORMAT, USER_ALIAS);

    @Autowire
    public UserRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper,
            SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User[] user = {null};
        Set<Long> walletsId = new HashSet<>();
        Set<Long> rolesId = new HashSet<>();
        template.query(SELECT_EAGER + " WHERE u.username = ?", pss -> pss.setString(1, username), rs -> {
            getUserEager(user, walletsId, rolesId, rs);
        });
        return Optional.ofNullable(user[0]);
    }

    @Override
    public Optional<User> findById(Long id) {
        User[] user = {null};
        Set<Long> walletsId = new HashSet<>();
        Set<Long> rolesId = new HashSet<>();
        template.query(SELECT_EAGER + " WHERE u.id = ?", pss -> pss.setLong(1, id), rs -> {
            getUserEager(user, walletsId, rolesId, rs);
        });
        return Optional.ofNullable(user[0]);
    }

    private void getUserEager(User[] user, Set<Long> walletsId, Set<Long> rolesId, ResultSet rs) {
        if (user[0] == null) {
            user[0] = entityMapper.mapAs(rs, entityClass, "u");
            user[0].setWallets(new HashSet<>());
            user[0].setRoles(new HashSet<>());
        }
        Wallet wallet = entityMapper.mapAs(rs, Wallet.class, "w");
        if (!walletsId.contains(wallet.getId()) && wallet.getId() != null) {
            wallet.setMoneyCurrency(entityMapper.mapAs(rs, AppCurrency.class, "ac"));
            user[0].getWallets().add(wallet);
            walletsId.add(wallet.getId());
        }
        Role role = entityMapper.mapAs(rs, Role.class, "r");
        if (!rolesId.contains(role.getId()) && role.getId() != null) {
            user[0].getRoles().add(role);
            rolesId.add(role.getId());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User[] user = {null};
        Set<Long> walletsId = new HashSet<>();
        Set<Long> rolesId = new HashSet<>();
        template.query(SELECT_EAGER + " WHERE u.email = ?", pss -> pss.setString(1, email), rs -> {
            getUserEager(user, walletsId, rolesId, rs);
        });
        return Optional.ofNullable(user[0]);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return template.query("SELECT u.id as u_id FROM users as u where u.username = ?", ps -> ps.setString(1, username),
                ResultSet::next);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return template.query("SELECT u.id as u_id FROM users as u where u.email = ?", ps -> ps.setString(1, email),
                ResultSet::next);
    }


    @Override
    public Page<User> findAll(MasterSearch masterSearch) {
        PageRequest request = masterSearch.getPageRequest();
        List<Object> dynamicPartValue = new ArrayList<>();
        String dynamic = generateDynamic(masterSearch, dynamicPartValue);
        return getUserDynamic(request, dynamicPartValue, dynamic);
    }

    @Override
    public Page<User> findAll(UserSearch userSearch) {
        PageRequest request = userSearch.getPageRequest();
        List<Object> dynamicPartValue = new ArrayList<>();
        String dynamic = generateDynamic(userSearch, dynamicPartValue);
        return getUserDynamic(request, dynamicPartValue, dynamic);
    }

    private Page<User> getUserDynamic(PageRequest request, List<Object> dynamicPartValue, String dynamic) {
        List<User> users = new ArrayList<>();
        String finalQuery;
        String countQuery;
        if (dynamic == null) {
            finalQuery = SELECT_EAGER_ONE_TO_ONE;
            countQuery = String.format(COUNT_FROM_FORMAT, String.format(SELECT_EAGER_ONE_TO_ONE_FORMAT, "u.id"));
        } else {
            finalQuery = SELECT_EAGER_ONE_TO_ONE + " WHERE u.id in ( " + String.format(SELECT_EAGER_FORMAT,
                    " DISTINCT u.id ") + dynamic + " )";
            countQuery = String.format(COUNT_FROM_FORMAT,
                    String.format(SELECT_EAGER_ONE_TO_ONE_FORMAT, "u.id") + " WHERE u.id in ( " + String.format(
                            SELECT_EAGER_FORMAT, " DISTINCT u.id ") + dynamic + " )");
        }
        template.query(finalQuery + queryGenerator.order(entitySqlDefinition, request.getSort(),
                new SqlAliasTableNaming("u")) + " LIMIT ? , ? ", ps -> {
            int currentPosition = setDynamicPart(dynamicPartValue, ps);
            ps.setLong(currentPosition, request.getOffset());
            ps.setInt(currentPosition + 1, request.getPageSize());
        }, rs -> {
            User user = getUser(rs);
            users.add(user);
        });
        long total = template.query(countQuery, ps -> setDynamicPart(dynamicPartValue, ps), this::getCount);
        return new PageImpl<>(users, request, total);
    }

    private User getUser(ResultSet rs) {
        User user = entityMapper.mapAs(rs, entitySqlDefinition, "u");
        user.setRoles(new HashSet<>());
        template.query(
                "SELECT "+ ROLES_ALIAS +" FROM roles as r left join user_has_role as uhr on r.id = uhr.role_id where uhr.user_id = ?",
                ps -> ps.setLong(1, user.getId()), rs1 -> {
                    Role role = entityMapper.mapAs(rs1, Role.class, "r");
                    user.getRoles().add(role);
                });
        return user;
    }

    private String generateDynamic(MasterSearch masterSearch, List<Object> dynamicPartValue) {
        List<String> dynamicPart = new ArrayList<>();
        dynamicPart.add(" r.name = ? ");
        dynamicPartValue.add(RoleEnum.MASTER.name());
        if (masterSearch.getUsername() != null && !masterSearch.getUsername().isBlank()) {
            dynamicPart.add(" u.username LIKE ? ");
            dynamicPartValue.add("%" + masterSearch.getUsername() + "%");
        }
        return constructDynamic(dynamicPart);
    }

    private String constructDynamic(List<String> dynamicPart) {
        if (dynamicPart.isEmpty()) {
            return null;
        } else {
            return " WHERE " + String.join(" AND ", dynamicPart);
        }
    }


    private String generateDynamic(UserSearch userSearch, List<Object> dynamicPartValue) {
        List<String> dynamicPart = new ArrayList<>();
        if (userSearch.getUsername() != null && !userSearch.getUsername().isBlank()) {
            dynamicPart.add(" u.username LIKE ? ");
            dynamicPartValue.add("%" + userSearch.getUsername() + "%");
        }
        return constructDynamic(dynamicPart);
    }

    @Override
    protected User onInsert(User entity,
            IdFieldDefinition idFieldDefinition) throws ReflectiveOperationException {
        entity.setCreationDate(Instant.now());
        entity.setLastModifiedDate(Instant.now());
        entity.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return super.onInsert(entity, idFieldDefinition);
    }

    @Override
    protected User onUpdate(User entity,
            IdFieldDefinition idFieldDefinition) throws ReflectiveOperationException {
        entity.setLastModifiedDate(Instant.now());
        entity.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return super.onUpdate(entity, idFieldDefinition);
    }
}
