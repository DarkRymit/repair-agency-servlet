package com.epam.finalproject.framework.data.jdbc;

import com.epam.finalproject.framework.data.jdbc.support.ColumnMapRowMapper;
import com.epam.finalproject.framework.data.jdbc.support.DataSourceUtils;
import com.epam.finalproject.framework.data.jdbc.support.JdbcUtils;
import com.epam.finalproject.framework.data.jdbc.support.RowMapperResultSetExtractor;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class JdbcTemplate {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JdbcTemplate.class);
    private final DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T execute(ConnectionCallback<T> action) throws DataAccessException {
        Connection con = DataSourceUtils.getConnection(dataSource);
        T result;
        try {
            Connection conToUse = createConnectionProxy(con);
            result = action.doInConnection(conToUse);
        } catch (SQLException e) {
            throw wrapException("ConnectionCallback", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }

        return result;
    }


    public <T> T execute(PreparedStatementCreator creator, PreparedStatementCallback<T> action) throws DataAccessException {
        return execute(con -> {
            PreparedStatement ps = null;
            T result;
            try {
                ps = creator.create(con);
                result = action.doInPreparedStatement(ps);
            } catch (SQLException e) {
                throw new SQLException("PreparedStatementCallback", e);
            } finally {
                JdbcUtils.closeStatement(ps);
            }
            return result;
        });
    }

    public <T> T execute(String sql, PreparedStatementCallback<T> action) throws DataAccessException {
        return this.execute(connection -> connection.prepareStatement(sql), action);
    }

    public <T> T query(PreparedStatementCreator preparedStatementCreator, final PreparedStatementSetter preparedStatementSetter, final ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return execute(preparedStatementCreator, preparedStatement -> {
            ResultSet resultSet = null;
            T result;
            try {
                if (preparedStatementSetter != null) {
                    preparedStatementSetter.setValues(preparedStatement);
                }
                resultSet = preparedStatement.executeQuery();
                result = resultSetExtractor.extractData(resultSet);
            } finally {
                JdbcUtils.closeResultSet(resultSet);
            }
            return result;
        });
    }

    public <T> T query(PreparedStatementCreator creator, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return query(creator, null, resultSetExtractor);
    }

    public <T> List<T> query(PreparedStatementCreator psc, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws DataAccessException {
        return query(psc, pss, new RowMapperResultSetExtractor<T>(rowMapper));
    }

    public <T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper) throws DataAccessException {
        return query(psc, null, rowMapper);
    }

    public <T> T query(String sql, PreparedStatementSetter preparedStatementSetter, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return query(con -> con.prepareStatement(sql), preparedStatementSetter, resultSetExtractor);
    }

    public <T> T query(String sql, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return query(sql, null, resultSetExtractor);
    }

    public <T> List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws DataAccessException {
        return query(sql, pss, new RowMapperResultSetExtractor<T>(rowMapper));
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return query(sql, null, rowMapper);
    }

    public void query(PreparedStatementCreator psc, RowCallbackHandler rch) throws DataAccessException {
        this.query(psc, new RowCallbackHandlerResultSetExtractor(rch));
    }

    public void query(String sql, PreparedStatementSetter pss, RowCallbackHandler rch) throws DataAccessException {
        this.query(sql, pss, new RowCallbackHandlerResultSetExtractor(rch));
    }

    public void query(String sql, RowCallbackHandler rowCallbackHandler) {
        this.query(sql, null, new RowCallbackHandlerResultSetExtractor(rowCallbackHandler));
    }

    public int update(PreparedStatementCreator psc) throws DataAccessException {
        return this.update(psc, null, null);
    }

    public int update(String sql) throws DataAccessException {
        return this.update(connection -> connection.prepareStatement(sql), null, null);
    }

    public int update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        return this.update(connection -> connection.prepareStatement(sql), pss, null);
    }

    public int update(String sql, PreparedStatementSetter pss, List<Map<String, Object>> generatedKeyHolder) throws DataAccessException {
        return this.update(connection ->  connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS), pss, generatedKeyHolder);
    }

    public int update(PreparedStatementCreator psc, PreparedStatementSetter pss, List<Map<String, Object>> generatedKeyHolder) throws DataAccessException {
        return this.execute(psc, ps -> {
            if (pss != null) {
                pss.setValues(ps);
            }
            int rows = ps.executeUpdate();
            log.trace("SQL update affected {} rows", rows);

            if (generatedKeyHolder != null) {
                generatedKeyHolder.clear();
                ResultSet keys = ps.getGeneratedKeys();
                if (keys != null) {
                    try {
                        RowMapperResultSetExtractor<Map<String, Object>> rse = new RowMapperResultSetExtractor<>(new ColumnMapRowMapper());
                        generatedKeyHolder.addAll(rse.extractData(keys));
                        log.trace("Generated keys {}",generatedKeyHolder);
                    } finally {
                        JdbcUtils.closeResultSet(keys);
                    }
                }
            }

            return rows;
        });
    }

    protected DataAccessException wrapException(String task, SQLException ex) {
        return new DataAccessException(String.format("Task: %s throw %s", task, ex.getMessage()), ex);
    }

    protected Connection createConnectionProxy(Connection con) {
        return (Connection) Proxy.newProxyInstance(ConnectionProxy.class.getClassLoader(), new Class[]{ConnectionProxy.class}, new CloseSuppressingInvocationHandler(con));
    }

    private static class RowCallbackHandlerResultSetExtractor implements ResultSetExtractor<Void> {
        private final RowCallbackHandler rch;

        public RowCallbackHandlerResultSetExtractor(RowCallbackHandler rch) {
            this.rch = rch;
        }

        public Void extractData(ResultSet rs) throws SQLException {
            while (rs.next()) {
                this.rch.processRow(rs);
            }
            return null;
        }
    }

    private static class CloseSuppressingInvocationHandler implements InvocationHandler {
        private final Connection target;

        public CloseSuppressingInvocationHandler(Connection target) {
            this.target = target;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            switch (method.getName()) {
                case "equals":
                    return proxy == args[0];
                case "hashCode":
                    return System.identityHashCode(proxy);
                case "close":
                    return null;
                case "isClosed":
                    return false;
                case "getTargetConnection":
                    return this.target;
                case "unwrap":
                    return ((Class<?>) args[0]).isInstance(proxy) ? proxy : this.target.unwrap((Class<?>) args[0]);
                case "isWrapperFor":
                    return ((Class<?>) args[0]).isInstance(proxy) || this.target.isWrapperFor((Class<?>) args[0]);
                default:
                    try {
                        return method.invoke(this.target, args);
                    } catch (InvocationTargetException var6) {
                        throw var6.getTargetException();
                    }
            }
        }
    }
}
