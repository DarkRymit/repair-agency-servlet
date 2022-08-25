package com.epam.finalproject.framework.data.jdbc.support;

import com.epam.finalproject.framework.data.jdbc.ResultSetExtractor;
import com.epam.finalproject.framework.data.jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RowMapperResultSetExtractor<T> implements ResultSetExtractor<List<T>> {
    private final RowMapper<T> rowMapper;

    public RowMapperResultSetExtractor(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    public List<T> extractData(ResultSet rs) throws SQLException {
        List<T> results = new ArrayList();
        int rowNum = 0;
        while(rs.next()) {
            results.add(this.rowMapper.mapRow(rs, rowNum++));
        }
        return results;
    }
}