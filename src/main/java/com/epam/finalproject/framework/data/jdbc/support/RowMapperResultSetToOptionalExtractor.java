package com.epam.finalproject.framework.data.jdbc.support;

import com.epam.finalproject.framework.data.jdbc.ResultSetExtractor;
import com.epam.finalproject.framework.data.jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RowMapperResultSetToOptionalExtractor<T> implements ResultSetExtractor<Optional<T>> {
    private final RowMapper<T> rowMapper;

    public RowMapperResultSetToOptionalExtractor(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    public Optional<T> extractData(ResultSet rs) throws SQLException {
        Optional<T> result = Optional.empty();
        if(rs.next()) {
            result = Optional.ofNullable(this.rowMapper.mapRow(rs, 1));
        }
        return result;
    }
}