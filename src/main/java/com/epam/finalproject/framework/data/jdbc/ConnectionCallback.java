package com.epam.finalproject.framework.data.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionCallback<T> {
    T doInConnection(Connection connection) throws SQLException;
}
