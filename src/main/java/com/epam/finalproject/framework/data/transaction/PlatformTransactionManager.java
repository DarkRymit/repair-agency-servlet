package com.epam.finalproject.framework.data.transaction;

public interface PlatformTransactionManager {
    TransactionStatus getTransaction() throws TransactionException;

    void commit(TransactionStatus transaction) throws TransactionException;

    void rollback(TransactionStatus transaction) throws TransactionException;
}
