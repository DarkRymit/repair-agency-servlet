package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageImpl;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.Wallet;
import com.epam.finalproject.repository.WalletRepository;

import java.util.List;
import java.util.Optional;

import static com.epam.finalproject.repository.impl.SqlAliasConstants.*;

@Component
public class WalletRepositorySQL extends SqlAnnotationDrivenRepository<Wallet> implements WalletRepository {


    public static final String SELECT_EAGER = "SELECT "+WALLETS_ALIAS+","+APP_CURRENCIES_ALIAS+"FROM wallets as w " +
            "left join app_currencies as ac on w.currency_id = ac.id";
    public static final String SELECT_EAGER_FORMAT = "SELECT %s " +
            "FROM wallets as w left join app_currencies as ac on w.currency_id = ac.id";

    @Autowire
    public WalletRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper,
            SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, Wallet.class);
    }

    @Override
    public List<Wallet> findAllByUser_Username(String username) {
        return template.query(SELECT_EAGER + " LEFT JOIN users as u on w.user_id = u.id WHERE u.username = ? ",
                pss -> pss.setString(1, username), (rs, rowNum) -> {
                    Wallet wallet = entityMapper.mapAs(rs, entitySqlDefinition, "w");
                    wallet.setMoneyCurrency(entityMapper.mapAs(rs, AppCurrency.class, "ac"));
                    return wallet;
                });
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return template.query(SELECT_EAGER + " WHERE w.id = ? ", pss -> pss.setLong(1, id),
                wrapToOptional((rs, rowNum) -> {
                    Wallet wallet = entityMapper.mapAs(rs, entitySqlDefinition, "w");
                    wallet.setMoneyCurrency(entityMapper.mapAs(rs, AppCurrency.class, "ac"));
                    return wallet;
                }));
    }

    @Override
    public Page<Wallet> findAllByUser_Username(Pageable pageable, String username) {
        List<Wallet> wallets = template.query(
                SELECT_EAGER + " LEFT JOIN users as u on w.user_id = u.id WHERE u.username = ? LIMIT ? , ?", ps -> {
                    ps.setString(1, username);
                    ps.setLong(2, pageable.getOffset());
                    ps.setInt(3, pageable.getPageSize());
                }, (rs, rowNum) -> {
                    Wallet wallet = entityMapper.mapAs(rs, entitySqlDefinition, "w");
                    wallet.setMoneyCurrency(entityMapper.mapAs(rs, AppCurrency.class, "ac"));
                    return wallet;
                });
        long count = template.query(String.format(COUNT_FROM_FORMAT,String.format(SELECT_EAGER_FORMAT,"w.id")
                        + " LEFT JOIN users as u on w.user_id = u.id WHERE u.username = ? "),
                pss -> pss.setString(1, username), this::getCount);
        return new PageImpl<>(wallets, pageable, count);
    }

    @Override
    public Optional<Wallet> findDistinctByNameAndUser_Username(String name, String username) {
        return Optional.empty();
    }
}
