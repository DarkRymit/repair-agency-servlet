package com.epam.finalproject.framework.data.jdbc;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String msg) {
        super(msg);
    }

    public DataAccessException(String msg,Throwable cause) {
        super(msg, cause);
    }

}