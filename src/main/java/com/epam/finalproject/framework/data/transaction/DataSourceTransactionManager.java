package com.epam.finalproject.framework.data.transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class DataSourceTransactionManager implements PlatformTransactionManager{

    private final DataSource dataSource;

    public DataSourceTransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public TransactionStatus getTransaction() throws TransactionException {
        try {
            Connection connection = (Connection) TransactionSynchronizationManager.getResource(dataSource);
            if (connection == null){
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);
                TransactionSynchronizationManager.bindResource(dataSource,connection);
                TransactionSynchronizationManager.setActualTransactionActive(true);
            }
            DataSourceTransaction transaction = new DataSourceTransaction(connection);
            return new DefaultTransactionStatus(transaction);
        } catch (SQLException e) {
            throw new TransactionException("Cant get transaction",e);
        }
    }

    @Override
    public void commit(TransactionStatus transaction) throws TransactionException {
        if (transaction.isCompleted()){
            throw new TransactionException(String.format("Transaction committed already %s",transaction));
        }

        DataSourceTransaction dataSourceTransaction = (DataSourceTransaction) transaction.getTransaction();

        dataSourceTransaction.commit();

        ((DefaultTransactionStatus) transaction).setCompleted();

        TransactionSynchronizationManager.unbindResource(dataSource);
        TransactionSynchronizationManager.setActualTransactionActive(false);
    }

    @Override
    public void rollback(TransactionStatus transaction) throws TransactionException {
        if (transaction.isCompleted()){
            throw new TransactionException(String.format("Transaction rollback already %s",transaction));
        }

        DataSourceTransaction dataSourceTransaction = (DataSourceTransaction) transaction.getTransaction();

        dataSourceTransaction.rollback();

        ((DefaultTransactionStatus) transaction).setCompleted();

        TransactionSynchronizationManager.unbindResource(dataSource);
        TransactionSynchronizationManager.setActualTransactionActive(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataSourceTransactionManager that = (DataSourceTransactionManager) o;
        return Objects.equals(dataSource, that.dataSource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSource);
    }
}
