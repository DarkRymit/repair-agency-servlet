package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.mapping.translators.*;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.model.entity.AppLocale;
import com.epam.finalproject.model.entity.Wallet;
import com.epam.finalproject.support.DatabaseSetupExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DatabaseSetupExtension.class)
class WalletRepositorySQLTest {

    static JdbcTemplate template;
    static SqlEntityMapper entityMapper;
    static SqlEntityQueryGenerator queryGenerator;
    static AnnotationSqlDefinitionReader definitionReader;
    WalletRepositorySQL walletRepositorySQL;

    @BeforeAll
    static void setup() {
        DataSource dataSource = DatabaseSetupExtension.getDataSource();
        template = new JdbcTemplate(dataSource);
        definitionReader = new AnnotationSqlDefinitionReader();
        entityMapper = new SqlEntityMapper(definitionReader,
                List.of(new BigDecimalTranslator(), new InstantTranslator(), new LongTranslator(),
                        new IntegerTranslator(), new ReferenceIdTranslator(definitionReader),
                        new SimpleEnumTranslator(), new StringTranslator()));
        queryGenerator = new SqlEntityQueryGenerator(definitionReader);
    }

    @BeforeEach
    void init() {
        walletRepositorySQL = new WalletRepositorySQL(template, entityMapper, queryGenerator, definitionReader);
    }



    @Test
    void findById() {
        Wallet wallet = walletRepositorySQL.findById(1L).orElseThrow();
        assertEquals(1L, wallet.getId());
        assertEquals(0, wallet.getMoneyAmount().compareTo(BigDecimal.valueOf(100.00)));
        assertEquals("USD", wallet.getMoneyCurrency().getCode());
    }

    @Test
    void existsById() {
        assertTrue(walletRepositorySQL.existsById(1L));
    }

    @Test
    void findAll() {
        List<Wallet> wallets = walletRepositorySQL.findAll();
        assertFalse(wallets.isEmpty());
        assertTrue(wallets.size() > 1);
    }

    @Test
    void count() {
        assertTrue(walletRepositorySQL.count() > 2);
    }

    @Test
    void deleteById() {
        walletRepositorySQL.deleteById(3L);
        assertFalse(walletRepositorySQL.existsById(3L));
        List<Wallet> wallets = walletRepositorySQL.findAll();
        assertFalse(wallets.isEmpty());
    }

    @Test
    void delete() {
        walletRepositorySQL.delete(walletRepositorySQL.findById(3L).orElseThrow());
        assertFalse(walletRepositorySQL.existsById(3L));
        List<Wallet> wallets = walletRepositorySQL.findAll();
        assertFalse(wallets.isEmpty());
    }

    @Test
    void testFindAll() {
        List<Wallet> wallets = walletRepositorySQL.findAll(Sort.by("id").descending());
        assertFalse(wallets.isEmpty());
        assertTrue(wallets.size() > 1);
        assertNotEquals(1L, wallets.get(0).getId());
    }

    @Test
    void testFindAll1() {
        Page<Wallet> wallets = walletRepositorySQL.findAll(PageRequest.of(0, 1, Sort.by("id").descending()));
        assertFalse(wallets.getContent().isEmpty());
        assertEquals(1, wallets.getContent().size());
        assertEquals(5L, wallets.getContent().get(0).getId());
    }

    @Test
    void findAllByUser_Username() {
        List<Wallet> wallets = walletRepositorySQL.findAllByUser_Username("RedStriker");
        assertFalse(wallets.isEmpty());
        assertTrue(wallets.size() > 1);
        assertEquals(1L, wallets.get(0).getId());
    }


    @Test
    void testFindAllByUser_Username() {
        Page<Wallet> wallets = walletRepositorySQL.findAllByUser_Username(
                PageRequest.of(0, 1, Sort.by("id").descending()), "RedStriker");
        assertFalse(wallets.getContent().isEmpty());
        assertEquals(1, wallets.getContent().size());
        assertEquals(1L, wallets.getContent().get(0).getId());
    }

    @Test
    void findDistinctByNameAndUser_Username() {
        Wallet wallet = walletRepositorySQL.findDistinctByNameAndUser_Username("Default", "RedStriker").orElseThrow();
        assertEquals(1L, wallet.getId());
        assertEquals(0, wallet.getMoneyAmount().compareTo(BigDecimal.valueOf(100.00)));
        assertEquals("USD", wallet.getMoneyCurrency().getCode());
    }
}