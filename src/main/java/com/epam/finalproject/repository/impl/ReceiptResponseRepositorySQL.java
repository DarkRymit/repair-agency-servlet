package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageImpl;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.jdbc.ResultSetExtractor;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.repository.ReceiptResponseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class ReceiptResponseRepositorySQL extends SqlAnnotationDrivenRepository<ReceiptResponse> implements ReceiptResponseRepository {

    private static final String SELECT_EAGER = "SELECT rs.*,r.*, m.*, u.*, st.*,d.*,i.*,rw.*,ac.* FROM master_responses as rs left join receipts AS r on rs.receipt_id=r.id LEFT JOIN users AS m ON r.master_id = m.id LEFT JOIN users AS u ON r.user_id = u.id LEFT JOIN receipt_statuses AS st ON r.receipt_status_id = st.id LEFT JOIN receipt_deliveries AS d ON r.id = d.receipt_id LEFT JOIN receipt_items AS i ON r.id = i.receipt_id LEFT JOIN repair_works AS rw ON rw.id = i.repair_work_id LEFT JOIN app_currencies AS ac ON r.currency_id = ac.id";


    @Autowire
    public ReceiptResponseRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper, SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, ReceiptResponse.class);
    }


    @Override
    public Page<ReceiptResponse> findAllFetchReceiptByReceipt_User_Username(String username, Pageable pageable) {
        Map<Long, ReceiptResponse> receiptResponseMap = new HashMap<>();
        template.query(SELECT_EAGER, rs -> {
            ReceiptResponse response = receiptResponseMap.get(rs.getLong("rs.id"));
            if (response == null) {
                response = entityMapper.mapAs(rs, entityClass,"rs");
                response.setReceipt(getReceipt(rs));
                receiptResponseMap.put(response.getId(),response);
            }
        });
        long total = count();
        return new PageImpl<>(receiptResponseMap.values(),pageable,total);
    }

    @Override
    public Page<ReceiptResponse> findAllFetchReceiptByReceipt_Master_Username(String username, Pageable pageable) {
        Map<Long, ReceiptResponse> receiptResponseMap = new HashMap<>();
        template.query(SELECT_EAGER, rs -> {
            ReceiptResponse response = receiptResponseMap.get(rs.getLong("rs.id"));
            if (response == null) {
                response = entityMapper.mapAs(rs, entityClass,"rs");
                response.setReceipt(getReceipt(rs));
                receiptResponseMap.put(response.getId(),response);
            }
        });
        long total = count();
        return new PageImpl<>(receiptResponseMap.values(),pageable,total);
    }

    @Override
    public Optional<ReceiptResponse> findByReceipt_Id(Long receiptId) {
        final ReceiptResponse[] response = {null};
        template.query(SELECT_EAGER + "WHERE r.id = ?", pss -> pss.setLong(1, receiptId), rs -> {
            if (response[0] == null) {
                response[0] = entityMapper.mapAs(rs, entityClass,"rs");
                response[0].setReceipt(getReceipt(rs));
            }
            ReceiptItem receiptItem = entityMapper.mapAs(rs,ReceiptItem.class,"i");
            receiptItem.setRepairWork(entityMapper.mapAs(rs,RepairWork.class,"rw"));
            response[0].getReceipt().getItems().add(receiptItem);
        });
        return Optional.of(response[0]);
    }

    private Receipt getReceipt(ResultSet rs) throws SQLException {
        Receipt receipt = entityMapper.mapAs(rs, Receipt.class,"r");
        if (rs.getObject("m.id") != null) {
            receipt.setMaster(entityMapper.mapAs(rs, User.class, "m"));
        } else {
            receipt.setMaster(null);
        }
        receipt.setUser(entityMapper.mapAs(rs, User.class,"u"));
        receipt.setStatus(entityMapper.mapAs(rs, ReceiptStatus.class,"st"));
        receipt.setDelivery(entityMapper.mapAs(rs, ReceiptDelivery.class,"d"));
        receipt.setPriceCurrency(entityMapper.mapAs(rs, AppCurrency.class,"ac"));
        receipt.setItems(new HashSet<>());
        return receipt;
    }

    @Override
    public boolean existsByReceipt_Id(Long id) {
        return template.query("SELECT r.id FROM master_responses as rs left join receipts as r on rs.receipt_id=r.id WHERE r.id = ? LIMIT 1", pss -> pss.setLong(1, id), new ResultSetExtractor<Boolean>() {
            @Override
            public Boolean extractData(ResultSet rs) throws SQLException {
                return rs.next();
            }
        });
    }
}
