package com.epam.finalproject.framework.data.transaction;

public interface TransactionStatus {
    Object getTransaction() throws TransactionException;

    boolean isCompleted();
}
