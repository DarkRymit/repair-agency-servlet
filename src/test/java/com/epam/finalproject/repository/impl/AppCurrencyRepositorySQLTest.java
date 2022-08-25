package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.beans.annotation.Configuration;
import com.epam.finalproject.framework.beans.factory.config.BeanScope;
import com.epam.finalproject.framework.beans.factory.support.DefaultBeanDefinition;
import com.epam.finalproject.framework.beans.factory.util.BeanUtils;
import com.epam.finalproject.framework.context.ManualConfigurableApplicationContext;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.mapping.translators.*;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.framework.scanner.ClassPathScanner;
import com.epam.finalproject.framework.web.annotation.Controller;
import com.epam.finalproject.framework.web.annotation.Service;
import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.service.impl.AppCurrencyServiceImpl;
import com.epam.finalproject.support.DatabaseSetupExtension;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DatabaseSetupExtension.class)
class AppCurrencyRepositorySQLTest {

    static JdbcTemplate template;
    static SqlEntityMapper entityMapper;
    static SqlEntityQueryGenerator queryGenerator;
    static AnnotationSqlDefinitionReader definitionReader;
    AppCurrencyRepositorySQL appCurrencyRepository;

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
        appCurrencyRepository = new AppCurrencyRepositorySQL(template, entityMapper, queryGenerator, definitionReader);
    }


    @Test
    void save() {
    }

    @Test
    void findById() {
       AppCurrency currency = appCurrencyRepository.findById(1L).orElseThrow();
       assertEquals(1L,currency.getId());
       assertEquals("USD",currency.getCode());
    }

    @Test
    void existsById() {
        assertTrue(appCurrencyRepository.existsById(1L));
    }

    @Test
    void findAll() {
        List<AppCurrency> currencies = appCurrencyRepository.findAll();
        assertFalse(currencies.isEmpty());
        assertTrue(currencies.size()>1);
    }

    @Test
    void count() {
        assertTrue(appCurrencyRepository.count()>2);
    }

    @Test
    void deleteById() {
        appCurrencyRepository.deleteById(3L);
        assertFalse(appCurrencyRepository.existsById(3L));
        List<AppCurrency> currencies = appCurrencyRepository.findAll();
        assertFalse(currencies.isEmpty());
    }

    @Test
    void delete() {
        AppCurrency currency = appCurrencyRepository.findById(3L).orElseThrow();
        appCurrencyRepository.delete(currency);
        assertFalse(appCurrencyRepository.existsById(3L));
    }

    @Test
    void testFindAll() {
        List<AppCurrency> currencies = appCurrencyRepository.findAll(Sort.by("id").descending());
        assertFalse(currencies.isEmpty());
        assertTrue(currencies.size()>1);
        assertNotEquals(1L,currencies.get(0).getId());
        assertEquals(3L,currencies.get(0).getId());
    }

    @Test
    void testFindAll1() {
        Page<AppCurrency> currencies = appCurrencyRepository.findAll(PageRequest.of(0,1,Sort.by("code").descending()));
        assertFalse(currencies.getContent().isEmpty());
        assertEquals(1,currencies.getContent().size());
        assertEquals(1L,currencies.getContent().get(0).getId());
    }

    @Test
    void findByCode() {
        AppCurrency currency = appCurrencyRepository.findByCode("USD").orElseThrow();
        assertEquals(1L,currency.getId());
        assertEquals("USD",currency.getCode());
    }
}