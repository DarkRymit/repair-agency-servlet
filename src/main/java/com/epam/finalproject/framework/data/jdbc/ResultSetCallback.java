package com.epam.finalproject.framework.data.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetCallback<T> {
    T doInResultSet(ResultSet resultSet) throws SQLException;
}
