package com.epam.finalproject.framework.data.jdbc.support;

import com.epam.finalproject.framework.data.jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ColumnMapRowMapper implements RowMapper<Map<String, Object>> {

    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Map<String, Object> mapOfColumnValues = createColumnMap(columnCount);

        for(int i = 1; i <= columnCount; ++i) {
            String column = JdbcUtils.lookupColumnName(rsmd, i);
            mapOfColumnValues.putIfAbsent(column, getColumnValue(rs, i));
        }

        return mapOfColumnValues;
    }

    protected Map<String, Object> createColumnMap(int columnCount) {
        return new LinkedHashMap<>(columnCount);
    }

    protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
        return rs.getObject(index);
    }
}
