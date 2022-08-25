package com.epam.finalproject.framework.data.jdbc.support;

import com.epam.finalproject.framework.util.StringUtils;
import org.slf4j.Logger;

import java.sql.*;

public abstract class JdbcUtils {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JdbcUtils.class);

    private JdbcUtils() {
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.debug("Could not close JDBC Connection", e);
            } catch (Exception e) {
                log.debug("Unexpected exception on closing JDBC Connection", e);
            }
        }

    }

    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.trace("Could not close JDBC Statement", e);
            } catch (Exception e) {
                log.trace("Unexpected exception on closing JDBC Statement", e);
            }
        }

    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.trace("Could not close JDBC ResultSet", e);
            } catch (Exception e) {
                log.trace("Unexpected exception on closing JDBC ResultSet", e);
            }
        }

    }

    public static String lookupColumnName(ResultSetMetaData resultSetMetaData, int columnIndex) throws SQLException {
        return resultSetMetaData.getColumnLabel(columnIndex);
    }
}
