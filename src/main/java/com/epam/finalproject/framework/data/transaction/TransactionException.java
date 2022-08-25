package com.epam.finalproject.framework.data.transaction;

public class TransactionException extends RuntimeException {
    public TransactionException(String msg) {
        super(msg);
    }

    public TransactionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
