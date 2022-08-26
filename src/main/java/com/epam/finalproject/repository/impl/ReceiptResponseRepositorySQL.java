package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageImpl;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.jdbc.ResultSetExtractor;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlAliasTableNaming;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.repository.ReceiptResponseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.epam.finalproject.repository.impl.SqlAliasConstants.*;

@Component
public class ReceiptResponseRepositorySQL extends SqlAnnotationDrivenRepository<ReceiptResponse>
        implements ReceiptResponseRepository {
    private static final String SELECT_EAGER_ONE_TO_ONE_FORMAT = "SELECT %s FROM master_responses as rs " +
            "LEFT JOIN receipts AS r on rs.receipt_id=r.id " +
            "LEFT JOIN repair_categories AS rc ON r.category_id = rc.id " +
            "LEFT JOIN users AS m ON r.master_id = m.id LEFT JOIN users AS u ON r.user_id = u.id " +
            "LEFT JOIN receipt_statuses AS st ON r.receipt_status_id = st.id " +
            "LEFT JOIN receipt_deliveries AS d ON r.id = d.receipt_id " +
            "LEFT JOIN app_currencies AS ac ON r.currency_id = ac.id";
    private static final String SELECT_EAGER_ONE_TO_ONE = String.format(SELECT_EAGER_ONE_TO_ONE_FORMAT,
            "rs.rating as rs_rating , rs.text as rs_text , rs.receipt_id as rs_receipt_id " + ","
                    + " st.id as st_id , st.name as st_name " + ","
                    + USER_MASTER_ALIAS + ","
                    + RECEIPT_ALIAS + ","
                    + REPAIR_CATEGORY_ALIAS + ","
                    + USER_ALIAS + ","
                    + "  d.receipt_id as d_receipt_id , d.city as d_city , d.country as d_country ," +
                    " d.local_address as d_local_address , d.postal_code as d_postal_code , d.state as d_state " + ","
                    + APP_CURRENCIES_ALIAS);


    @Autowire
    public ReceiptResponseRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper,
            SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, ReceiptResponse.class);
    }


    @Override
    public Page<ReceiptResponse> findAllFetchReceiptByReceipt_User_Username(String username, Pageable pageable) {
        Map<Long, ReceiptResponse> receiptResponseMap = new HashMap<>();
        template.query(SELECT_EAGER_ONE_TO_ONE + " WHERE u.username = ? " + queryGenerator.order(entitySqlDefinition,
                        pageable.getSort(), new SqlAliasTableNaming("rs")) + " LIMIT ? , ? ", ps -> {
                    ps.setString(1, username);
                    ps.setLong(2, pageable.getOffset());
                    ps.setInt(3, pageable.getPageSize());
                },
                rs -> {
                    ReceiptResponse response = receiptResponseMap.get(rs.getLong("rs_receipt_id"));
                    if (response == null) {
                        response = entityMapper.mapAs(rs, entityClass, "rs");
                        response.setReceipt(getReceipt(rs));
                        receiptResponseMap.put(response.getId(), response);
                    }
                });
        String countQuery = String.format(COUNT_FROM_FORMAT,
                String.format(SELECT_EAGER_ONE_TO_ONE_FORMAT, "rs.receipt_id") + " WHERE u.username = ? ");
        long total = template.query(countQuery, ps -> ps.setString(1, username), this::getCount);
        return new PageImpl<>(receiptResponseMap.values(), pageable, total);
    }

    @Override
    public Page<ReceiptResponse> findAll(Pageable pageable) {
        Map<Long, ReceiptResponse> receiptResponseMap = new HashMap<>();
        template.query(SELECT_EAGER_ONE_TO_ONE + queryGenerator.order(entitySqlDefinition,
                        pageable.getSort(), new SqlAliasTableNaming("rs")) + " LIMIT ? , ? ", ps -> {
                    ps.setLong(1, pageable.getOffset());
                    ps.setInt(2, pageable.getPageSize());
                },
                rs -> {
                    ReceiptResponse response = entityMapper.mapAs(rs, entityClass, "rs");
                    response.setReceipt(getReceipt(rs));
                    receiptResponseMap.put(response.getId(), response);

                });
        return new PageImpl<>(receiptResponseMap.values(), pageable, count());
    }

    @Override
    public Page<ReceiptResponse> findAllFetchReceiptByReceipt_Master_Username(String username, Pageable pageable) {
        Map<Long, ReceiptResponse> receiptResponseMap = new HashMap<>();
        template.query(SELECT_EAGER_ONE_TO_ONE + " WHERE m.username = ? " + queryGenerator.order(entitySqlDefinition,
                        pageable.getSort(), new SqlAliasTableNaming("rs")) + " LIMIT ? , ?", ps -> {
                    ps.setString(1, username);
                    ps.setLong(2, pageable.getOffset());
                    ps.setInt(3, pageable.getPageSize());
                },
                rs -> {
                    ReceiptResponse response = entityMapper.mapAs(rs, entityClass, "rs");
                    response.setReceipt(getReceipt(rs));
                    receiptResponseMap.put(response.getId(), response);
                });
        String countQuery = String.format(COUNT_FROM_FORMAT,
                String.format(SELECT_EAGER_ONE_TO_ONE_FORMAT, "rs.receipt_id") + " WHERE m.username = ? ");
        long total = template.query(countQuery, ps -> ps.setString(1, username), this::getCount);
        return new PageImpl<>(receiptResponseMap.values(), pageable, total);
    }

    @Override
    public Optional<ReceiptResponse> findByReceipt_Id(Long receiptId) {
        final ReceiptResponse[] response = new ReceiptResponse[1];
        template.query(SELECT_EAGER_ONE_TO_ONE + " WHERE r.id = ?", pss -> pss.setLong(1, receiptId), rs -> {
            response[0] = entityMapper.mapAs(rs, entityClass, "rs");
        });
        return Optional.of(response[0]);
    }

    private Receipt getReceipt(ResultSet rs) throws SQLException {
        Receipt receipt = entityMapper.mapAs(rs, Receipt.class, "r");

        if (rs.getObject("m_id") != null) {
            receipt.setMaster(entityMapper.mapAs(rs, User.class, "m"));
        } else {
            receipt.setMaster(null);
        }
        receipt.setCategory(entityMapper.mapAs(rs, RepairCategory.class, "rc"));
        receipt.setUser(entityMapper.mapAs(rs, User.class, "u"));
        receipt.setStatus(entityMapper.mapAs(rs, ReceiptStatus.class, "st"));
        receipt.setDelivery(entityMapper.mapAs(rs, ReceiptDelivery.class, "d"));
        receipt.setPriceCurrency(entityMapper.mapAs(rs, AppCurrency.class, "ac"));

        receipt.setItems(new HashSet<>());
        template.query(
                "SELECT" + RECEIPT_ITEMS_ALIAS + "," + REPAIR_WORK_ALIAS +
                        "FROM receipt_items AS i LEFT JOIN repair_works AS rw ON rw.id = i.repair_work_id WHERE i.receipt_id = ?",
                ps -> ps.setLong(1, receipt.getId()), rs1 -> {
                    ReceiptItem item = entityMapper.mapAs(rs1, ReceiptItem.class, "i");
                    item.setRepairWork(entityMapper.mapAs(rs1, RepairWork.class, "rw"));
                    item.setReceipt(receipt);
                    receipt.getItems().add(item);
                });
        return receipt;
    }

    @Override
    public boolean existsByReceipt_Id(Long id) {
        return template.query(
                "SELECT r.id FROM master_responses as rs left join receipts as r on rs.receipt_id=r.id WHERE r.id = ? LIMIT 1",
                pss -> pss.setLong(1, id), ResultSet::next);
    }
}
