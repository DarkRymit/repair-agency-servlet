package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageImpl;
import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.framework.data.jdbc.DataAccessException;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.IdFieldDefinition;
import com.epam.finalproject.framework.data.sql.mapping.SqlAliasTableNaming;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityDefinition;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.search.ReceiptSearch;
import com.epam.finalproject.model.search.ReceiptWithCustomerSearch;
import com.epam.finalproject.model.search.ReceiptWithMasterSearch;
import com.epam.finalproject.repository.ReceiptRepository;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.epam.finalproject.repository.impl.SqlAliasConstants.*;

@Component
public class ReceiptRepositorySQL extends SqlAnnotationDrivenRepository<Receipt> implements ReceiptRepository {


    private static final String FIND_EAGER_FORMAT = "SELECT  %s FROM receipts AS r " +
            "LEFT JOIN repair_categories AS rc ON r.category_id = rc.id LEFT JOIN users AS m ON r.master_id = m.id " +
            "LEFT JOIN users AS u ON r.user_id = u.id " +
            "LEFT JOIN receipt_statuses AS st ON r.receipt_status_id = st.id " +
            "LEFT JOIN receipt_deliveries AS d ON r.id = d.receipt_id " +
            "LEFT JOIN receipt_items AS i ON r.id = i.receipt_id " +
            "LEFT JOIN repair_works AS rw ON rw.id = i.repair_work_id  " +
            "LEFT JOIN app_currencies AS ac ON r.currency_id = ac.id";
    private static final String FIND_EAGER_ONE_TO_ONE_FORMAT = "SELECT %s  FROM receipts AS r " +
            "LEFT JOIN repair_categories AS rc ON r.category_id = rc.id " +
            "LEFT JOIN users AS m ON r.master_id = m.id LEFT JOIN users AS u ON r.user_id = u.id " +
            "LEFT JOIN receipt_statuses AS st ON r.receipt_status_id = st.id " +
            "LEFT JOIN receipt_deliveries AS d ON r.id = d.receipt_id  " +
            "LEFT JOIN app_currencies AS ac ON r.currency_id = ac.id";
    private static final String FIND_EAGER = String.format(FIND_EAGER_FORMAT, RECEIPT_ALIAS + "," +
            " st.id as st_id , st.name as st_name " + "," +
            USER_MASTER_ALIAS + "," +
            REPAIR_CATEGORY_ALIAS + "," +
            USER_ALIAS + "," +
            "  d.receipt_id as d_receipt_id , d.city as d_city , d.country as d_country ," +
            " d.local_address as d_local_address , d.postal_code as d_postal_code , d.state as d_state " + "," +
            RECEIPT_ITEMS_ALIAS + "," +
            REPAIR_WORK_ALIAS + "," +
            APP_CURRENCIES_ALIAS);
    private static final String FIND_BY_ID_SQL = FIND_EAGER + " WHERE r.id = ?";

    private static final String FIND_EAGER_ONE_TO_ONE = String.format(FIND_EAGER_ONE_TO_ONE_FORMAT,
            RECEIPT_ALIAS + "," +
                    " st.id as st_id , st.name as st_name " + "," +
                    USER_MASTER_ALIAS + "," +
                    REPAIR_CATEGORY_ALIAS + "," +
                    USER_ALIAS + "," +
                    "  d.receipt_id as d_receipt_id , d.city as d_city , d.country as d_country ," +
                    " d.local_address as d_local_address , d.postal_code as d_postal_code , d.state as d_state " + "," +
                    APP_CURRENCIES_ALIAS);


    @Autowire
    public ReceiptRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper,
            SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, Receipt.class);
    }

    @Override
    public Optional<Receipt> findById(Long aLong) {
        final Receipt[] receipt = {null};
        template.query(FIND_BY_ID_SQL, pss -> pss.setLong(1, aLong), rs -> {
            if (receipt[0] == null) {
                receipt[0] = entityMapper.mapAs(rs, entityClass, "r");
                if (rs.getObject("m_id") != null) {
                    receipt[0].setMaster(entityMapper.mapAs(rs, User.class, "m"));
                } else {
                    receipt[0].setMaster(null);
                }
                receipt[0].setCategory(entityMapper.mapAs(rs, RepairCategory.class, "rc"));
                receipt[0].setUser(entityMapper.mapAs(rs, User.class, "u"));
                receipt[0].setStatus(entityMapper.mapAs(rs, ReceiptStatus.class, "st"));
                receipt[0].setDelivery(entityMapper.mapAs(rs, ReceiptDelivery.class, "d"));
                receipt[0].setPriceCurrency(entityMapper.mapAs(rs, AppCurrency.class, "ac"));
                receipt[0].setItems(new HashSet<>());
            }
            ReceiptItem receiptItem = entityMapper.mapAs(rs, ReceiptItem.class, "i");
            receiptItem.setRepairWork(entityMapper.mapAs(rs, RepairWork.class, "rw"));
            receiptItem.setReceipt(receipt[0]);
            receipt[0].getItems().add(receiptItem);
        });
        return Optional.of(receipt[0]);
    }

    @Override
    public Page<Receipt> findAll(ReceiptWithMasterSearch receiptSearch) {
        PageRequest request = receiptSearch.getPageRequest();
        List<Object> dynamicPartValue = new ArrayList<>();
        String dynamic = generateDynamic(receiptSearch, dynamicPartValue);
        return getReceiptDynamic(request, dynamicPartValue, dynamic);
    }

    @Override
    public Page<Receipt> findAll(ReceiptWithCustomerSearch receiptSearch) {
        PageRequest request = receiptSearch.getPageRequest();
        List<Object> dynamicPartValue = new ArrayList<>();
        String dynamic = generateDynamic(receiptSearch, dynamicPartValue);
        return getReceiptDynamic(request, dynamicPartValue, dynamic);
    }

    @Override
    public Page<Receipt> findAll(ReceiptSearch receiptSearch) {
        PageRequest request = receiptSearch.getPageRequest();
        List<Object> dynamicPartValue = new ArrayList<>();
        String dynamic = generateDynamic(receiptSearch, dynamicPartValue);
        return getReceiptDynamic(request, dynamicPartValue, dynamic);
    }

    private PageImpl<Receipt> getReceiptDynamic(PageRequest request, List<Object> dynamicPartValue, String dynamic) {
        SqlAliasTableNaming naming = getSqlAliasTableNaming();
        List<Receipt> receiptMap = new ArrayList<>();
        String finalQuery;
        String countQuery;
        if (dynamic == null) {
            finalQuery = FIND_EAGER_ONE_TO_ONE;
            countQuery = String.format(COUNT_FROM_FORMAT, String.format(FIND_EAGER_ONE_TO_ONE_FORMAT, "r.id"));
        } else {
            finalQuery = FIND_EAGER_ONE_TO_ONE + dynamic;
            countQuery = String.format(COUNT_FROM_FORMAT,
                    String.format(FIND_EAGER_ONE_TO_ONE_FORMAT, "r.id") + dynamic);
        }
        template.query(
                finalQuery + queryGenerator.order(entitySqlDefinition, request.getSort(), naming) + " LIMIT ? , ? ",
                ps -> {
                    int currentPosition = setDynamicPart(dynamicPartValue, ps);
                    ps.setLong(currentPosition, request.getOffset());
                    ps.setInt(currentPosition + 1, request.getPageSize());
                }, rs -> {
                    Receipt receipt = getReceipt(rs);
                    receiptMap.add(receipt);
                });
        long total = template.query(countQuery, ps -> setDynamicPart(dynamicPartValue, ps), this::getCount);
        return new PageImpl<>(receiptMap, request, total);
    }

    private Receipt getReceipt(ResultSet rs) throws SQLException {
        Receipt receipt = entityMapper.mapAs(rs, entityClass, "r");

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


    private String generateDynamic(ReceiptWithMasterSearch receiptSearch, List<Object> dynamicPartValue) {
        List<String> dynamicPart = new ArrayList<>();
        List<String> inClause = new ArrayList<>();
        dynamicPart.add("m.id = ?");
        dynamicPartValue.add(receiptSearch.getMaster().getId());
        hasCustomerName(receiptSearch.getCustomerUsername(), dynamicPartValue, dynamicPart);
        hasStatuses(receiptSearch.getReceiptStatuses(), dynamicPartValue, inClause);
        return constructDynamic(dynamicPart, inClause);
    }

    private String generateDynamic(ReceiptWithCustomerSearch receiptSearch, List<Object> dynamicPartValue) {
        List<String> dynamicPart = new ArrayList<>();
        List<String> inClause = new ArrayList<>();
        dynamicPart.add("u.id = ?");
        dynamicPartValue.add(receiptSearch.getCustomer().getId());
        hasMasterName(receiptSearch.getMasterUsername(), dynamicPartValue, dynamicPart);
        hasStatuses(receiptSearch.getReceiptStatuses(), dynamicPartValue, inClause);
        return constructDynamic(dynamicPart, inClause);
    }

    private void hasStatuses(Set<ReceiptStatusEnum> statusEnums, List<Object> dynamicPartValue, List<String> inClause) {
        if (statusEnums != null && !statusEnums.isEmpty()) {
            int size = statusEnums.size();
            inClause.addAll(IntStream.range(0, size).mapToObj(e -> "?").collect(Collectors.toList()));
            dynamicPartValue.addAll(statusEnums
                    .stream()
                    .map(Enum::name)
                    .collect(Collectors.toList()));
        }
    }

    private String generateDynamic(ReceiptSearch receiptSearch, List<Object> dynamicPartValue) {
        List<String> dynamicPart = new ArrayList<>();
        List<String> inClause = new ArrayList<>();
        hasCustomerName(receiptSearch.getCustomerUsername(), dynamicPartValue, dynamicPart);
        hasMasterName(receiptSearch.getMasterUsername(), dynamicPartValue, dynamicPart);
        hasStatuses(receiptSearch.getReceiptStatuses(), dynamicPartValue, inClause);
        return constructDynamic(dynamicPart, inClause);
    }

    private void hasMasterName(String name, List<Object> dynamicPartValue, List<String> dynamicPart) {
        if (name != null && !name.isBlank()) {
            dynamicPart.add("m.username LIKE ?");
            dynamicPartValue.add("%" + name + "%");
        }
    }

    private void hasCustomerName(String name, List<Object> dynamicPartValue, List<String> dynamicPart) {
        if (name != null && !name.isBlank()) {
            dynamicPart.add("u.username LIKE ?");
            dynamicPartValue.add("%" + name + "%");
        }
    }

    private String constructDynamic(List<String> dynamicPart, List<String> inClause) {
        if (dynamicPart.isEmpty() && inClause.isEmpty()) {
            return null;
        } else {
            StringBuilder result = new StringBuilder(" WHERE ");
            if (!dynamicPart.isEmpty()) {
                result.append(String.join(" AND ", dynamicPart));
            }
            if (!inClause.isEmpty()) {
                result.append(!dynamicPart.isEmpty() ? " AND " : " ");
                result.append(inClause.stream().collect(Collectors.joining(",", "st.name IN (", ")")));
            }
            return result.toString();
        }
    }

    @Override
    public ReceiptDelivery saveDelivery(ReceiptDelivery entity) {
        SqlEntityDefinition<ReceiptDelivery> definition = definitionReader.getDefinition(ReceiptDelivery.class);
        return save(definition, entity);
    }

    @Override
    public void deleteDelivery(ReceiptDelivery entity) {
        template.update(queryGenerator.deleteById(ReceiptDelivery.class), ps -> ps.setLong(1, entity.getId()));
    }


    @Override
    public ReceiptItem saveItem(ReceiptItem entity) {
        try {
            ReceiptItem result;
            SqlEntityDefinition<ReceiptItem> definition = definitionReader.getDefinition(ReceiptItem.class);
            IdFieldDefinition idFieldDefinition = getIdColumn(definition);
            Method getId = idFieldDefinition.getIdGetter();
            if (getId.invoke(entity) == null) {
                result = onInsertItem(definition, idFieldDefinition, entity);
            } else {
                result = onUpdateItem(definition, idFieldDefinition, entity);
            }
            return result;
        } catch (ReflectiveOperationException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void deleteItem(ReceiptItem entity) {
        template.update(queryGenerator.deleteById(ReceiptItem.class), ps -> ps.setLong(1, entity.getId()));
    }

    protected ReceiptItem onInsertItem(SqlEntityDefinition<ReceiptItem> definition, IdFieldDefinition idFieldDefinition,
            ReceiptItem entity) throws ReflectiveOperationException {
        List<Map<String, Object>> keys = new ArrayList<>();
        template.update(queryGenerator.insertQuery(definition), pss -> {
            pss.setNull(1, Types.BIGINT);
            entityMapper.setValues(pss, definition, 2, entity);
        }, keys);
        Object id = keys.get(0).get("GENERATED_KEY");
        idFieldDefinition.getIdSetter().invoke(entity, ((BigInteger) id).longValue());
        return entity;
    }

    protected ReceiptItem onUpdateItem(SqlEntityDefinition<ReceiptItem> definition, IdFieldDefinition idFieldDefinition,
            ReceiptItem entity) throws ReflectiveOperationException {
        Long id = (Long) idFieldDefinition.getIdGetter().invoke(entity);
        template.update(queryGenerator.updateByIdQuery(definition), pss -> {
            pss.setLong(1, id);
            int end = entityMapper.setValues(pss, definition, 2, entity);
            pss.setLong(end, id);
        });
        return entity;
    }


    private SqlAliasTableNaming getSqlAliasTableNaming() {
        SqlAliasTableNaming naming = new SqlAliasTableNaming("r");
        naming.getFieldTables().put("status", new SqlAliasTableNaming("st"));
        naming.getFieldTables().put("priceCurrency", new SqlAliasTableNaming("ac"));
        return naming;
    }
}
