package com.epam.finalproject.framework.data.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowCallbackHandler {
    void processRow(ResultSet rs) throws SQLException;
}
