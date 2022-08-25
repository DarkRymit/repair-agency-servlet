package com.epam.finalproject.config;

import com.epam.finalproject.framework.beans.annotation.Bean;
import com.epam.finalproject.framework.beans.annotation.Configuration;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.transaction.DataSourceTransactionManager;
import com.epam.finalproject.framework.data.transaction.PlatformTransactionManager;
import org.h2.jdbcx.JdbcDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(clazz = DataSource.class)
    DataSource dataSource() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            return (DataSource) envContext.lookup("jdbc/MysqlHikari");
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Bean(clazz = JdbcTemplate.class)
    JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(clazz = DataSourceTransactionManager.class)
    PlatformTransactionManager platformTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
