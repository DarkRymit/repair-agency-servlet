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
import com.epam.finalproject.support.DatabaseSetupExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DatabaseSetupExtension.class)
class AppLocaleRepositorySQLTest {

    static JdbcTemplate template;
    static SqlEntityMapper entityMapper;
    static SqlEntityQueryGenerator queryGenerator;
    static AnnotationSqlDefinitionReader definitionReader;
    AppLocaleRepositorySQL appLocaleRepositorySQL;

    @BeforeAll
    static void getResources() {
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
    void setUp() {
        appLocaleRepositorySQL = new AppLocaleRepositorySQL(template, entityMapper, queryGenerator, definitionReader);
    }


    

    @Test
    void findById() {
       AppLocale locale = appLocaleRepositorySQL.findById(1L).orElseThrow();
       assertEquals(1L,locale.getId());
       assertEquals("en",locale.getLang());
    }

    @Test
    void existsById() {
        assertTrue(appLocaleRepositorySQL.existsById(1L));
    }

    @Test
    void findAll() {
        List<AppLocale> locales = appLocaleRepositorySQL.findAll();
        assertFalse(locales.isEmpty());
        assertTrue(locales.size()>1);
    }

    @Test
    void count() {
        assertTrue(appLocaleRepositorySQL.count()>2);
    }

    @Test
    void deleteById() {
        appLocaleRepositorySQL.deleteById(3L);
        assertFalse(appLocaleRepositorySQL.existsById(3L));
        List<AppLocale> locales = appLocaleRepositorySQL.findAll();
        assertFalse(locales.isEmpty());
    }

    @Test
    void delete() {
        AppLocale locale = appLocaleRepositorySQL.findById(3L).orElseThrow();
        appLocaleRepositorySQL.delete(locale);
        assertFalse(appLocaleRepositorySQL.existsById(3L));
    }

    @Test
    void testFindAll() {
        List<AppLocale> locales = appLocaleRepositorySQL.findAll(Sort.by("id").descending());
        assertFalse(locales.isEmpty());
        assertTrue(locales.size()>1);
        assertNotEquals(1L,locales.get(0).getId());
        assertEquals(3L,locales.get(0).getId());
    }

    @Test
    void testFindAll1() {
        Page<AppLocale> locales = appLocaleRepositorySQL.findAll(PageRequest.of(0,1,Sort.by("lang").descending()));
        assertFalse(locales.getContent().isEmpty());
        assertEquals(1,locales.getContent().size());
        assertEquals(2L,locales.getContent().get(0).getId());
    }

    @Test
    void findByCode() {
        AppLocale currency = appLocaleRepositorySQL.findByLang("en").orElseThrow();
        assertEquals(1L,currency.getId());
        assertEquals("en",currency.getLang());
    }
}