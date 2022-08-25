package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.ReceiptStatus;
import com.epam.finalproject.model.entity.ReceiptStatusFlow;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.repository.ReceiptStatusFlowRepository;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ReceiptStatusFlowRepositorySQL extends SqlAnnotationDrivenRepository<ReceiptStatusFlow> implements ReceiptStatusFlowRepository {

    public static final String SELECT_EAGER = "SELECT f.*,fs.*,ts.*,r.* FROM receipt_status_flows as f left join receipt_statuses as fs on f.from_status_id = fs.id left join receipt_statuses as ts on f.to_status_id = ts.id left join roles as r on f.role_id = r.id";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReceiptStatusFlowRepositorySQL.class);

    @Autowire
    public ReceiptStatusFlowRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper, SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, ReceiptStatusFlow.class);
    }

    @Override
    public List<ReceiptStatusFlow> findDistinctByRoleIn(Set<Role> roles) {
        String inString = getInParamString(roles.size());
        return template.query(SELECT_EAGER  + "  where r.id in (" + inString + ") ", ps -> {
            setRolesIn(1, roles, ps);
        }, (rs, rowNum) -> getReceiptStatusFlow(rs));
    }

    private ReceiptStatusFlow getReceiptStatusFlow(ResultSet rs) {
        ReceiptStatusFlow flow = entityMapper.mapAs(rs, entitySqlDefinition, "f");
        flow.setToStatus(entityMapper.mapAs(rs, ReceiptStatus.class, "ts"));
        flow.setFromStatus(entityMapper.mapAs(rs, ReceiptStatus.class, "fs"));
        flow.setRole(entityMapper.mapAs(rs, Role.class, "r"));
        return flow;
    }

    private int setRolesIn(int start, Collection<Role> roles, PreparedStatement ps) throws SQLException {
        int currentIndex = start;
        for (Role role : roles) {
            ps.setLong(currentIndex, role.getId());
            currentIndex++;
        }
        return currentIndex;
    }

    @Override
    public List<ReceiptStatusFlow> findDistinctByFromStatus_IdAndRoleIn(Long fromId, Set<Role> roles) {
        String inString = getInParamString(roles.size());
        return template.query(SELECT_EAGER + "  where fs.id = ? and r.id in (" + inString + ") ", ps -> {
            ps.setLong(1, fromId);
            setRolesIn(2, roles, ps);
        }, (rs, rowNum) -> getReceiptStatusFlow(rs));
    }


    @Override
    public boolean existsByFromStatusAndToStatusAndRoleIn(ReceiptStatus fromStatus, ReceiptStatus toStatus, Collection<Role> roles) {
        log.trace("Called exists with fromStatus {} toStatus {} to roles {}",fromStatus,toStatus,roles);
        String inString = getInParamString(roles.size());
        return template.query("SELECT rsf.id FROM receipt_status_flows as rsf where rsf.from_status_id = ? and rsf.to_status_id = ? and rsf.role_id in (" + inString + ") ", ps -> {
            ps.setLong(1, fromStatus.getId());
            ps.setLong(2, toStatus.getId());
            setRolesIn(3, roles, ps);
        }, ResultSet::next);
    }

    private String getInParamString(int roles) {
        return IntStream.rangeClosed(1, roles)
                .mapToObj(value -> "?")
                .collect(Collectors.joining(","));
    }
}
