package com.epam.finalproject.support;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.extension.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Objects;

public class DatabaseSetupExtension implements BeforeAllCallback, AfterAllCallback, AfterEachCallback {

    private static final ThreadLocal<DataSource> testDataSourceThreadLocal = new ThreadLocal<>();

    private Connection connection;
    private Savepoint savepoint;

    public DatabaseSetupExtension(){
    }

    private void setUpDataBase(Connection connection1) throws SQLException, IOException {
        RunScript.execute(connection1, load("schema.sql"));
        RunScript.execute(connection1, load("data-test.sql"));
    }

    private Reader load(String fileName) throws IOException {
        return new InputStreamReader(
                Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(fileName)));
    }

    @Override
    public void beforeAll(ExtensionContext context) throws SQLException, IOException {
        DataSource dataSource = DataSourceHolder.dataSource();
        connection = dataSource.getConnection();
        boolean defaultCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        setUpDataBase(connection);
        savepoint = connection.setSavepoint("before");
        testDataSourceThreadLocal.set(new TestDataSource(dataSource, new TestConnection(connection,defaultCommit,savepoint)));
    }

    @Override
    public void afterEach(ExtensionContext context) throws SQLException {
        connection.rollback(savepoint);
    }

    @Override
    public void afterAll(ExtensionContext context) throws SQLException {
        connection.close();
    }

    public static DataSource getDataSource(){
        return testDataSourceThreadLocal.get();
    }

}