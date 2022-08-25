package com.epam.finalproject.support;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class DataSourceHolder {

    public static DataSource dataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:file:./database/testdb;AUTO_SERVER=true");
        ds.setUser("sa");
        ds.setPassword("password");
        return ds;
    }

}
