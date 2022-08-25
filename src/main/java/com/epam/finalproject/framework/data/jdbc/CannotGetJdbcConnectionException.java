package com.epam.finalproject.framework.data.jdbc;

import java.sql.SQLException;

public class CannotGetJdbcConnectionException extends DataAccessException {
    public CannotGetJdbcConnectionException(String msg) {
        super(msg);
    }

    public CannotGetJdbcConnectionException(String msg, SQLException ex) {
        super(msg, ex);
    }
}
