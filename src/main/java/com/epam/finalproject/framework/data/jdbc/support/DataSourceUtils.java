package com.epam.finalproject.framework.data.jdbc.support;

import com.epam.finalproject.framework.data.jdbc.CannotGetJdbcConnectionException;
import com.epam.finalproject.framework.data.transaction.TransactionSynchronizationManager;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DataSourceUtils {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DataSourceUtils.class);

    private DataSourceUtils() {
    }

    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        try {
            return doGetConnection(dataSource);
        } catch (SQLException e) {
            throw new CannotGetJdbcConnectionException("Failed to obtain JDBC Connection", e);
        } catch (IllegalStateException e) {
            throw new CannotGetJdbcConnectionException("Failed to obtain JDBC Connection: " + e);
        }
    }

    public static Connection doGetConnection(DataSource dataSource) throws SQLException {
        Connection connection = (Connection) TransactionSynchronizationManager.getResource(dataSource);
        if (connection == null){
            log.debug("Fetching JDBC Connection from DataSource");
            connection = fetchConnection(dataSource);
        }
        return connection;
    }

    private static Connection fetchConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    public static void releaseConnection(Connection con, DataSource dataSource) {
        try {
            doReleaseConnection(con, dataSource);
        } catch (SQLException e) {
            log.debug("Could not close JDBC Connection from dataSource {}",dataSource, e);
        } catch (Exception e) {
            log.debug("Unexpected exception on closing JDBC Connection from dataSource {}",dataSource, e);
        }

    }

    public static void doReleaseConnection(Connection con, DataSource dataSource) throws SQLException {
        if (!TransactionSynchronizationManager.isActualTransactionActive()){
            doCloseConnection(con,dataSource);
        }
    }

    public static void doCloseConnection(Connection con,DataSource dataSource) throws SQLException {
        con.close();
        log.trace("Close JDBC Connection from dataSource {}",dataSource);
    }

}
