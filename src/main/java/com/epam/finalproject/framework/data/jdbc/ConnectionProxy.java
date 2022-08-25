package com.epam.finalproject.framework.data.jdbc;

import java.sql.Connection;

public interface ConnectionProxy extends Connection {
    Connection getTargetConnection();
}