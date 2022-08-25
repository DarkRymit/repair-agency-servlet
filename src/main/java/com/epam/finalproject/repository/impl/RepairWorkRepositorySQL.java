package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlAliasTableNaming;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.RepairWork;
import com.epam.finalproject.model.entity.RepairWorkLocalPart;
import com.epam.finalproject.model.entity.RepairWorkPrice;
import com.epam.finalproject.repository.RepairWorkRepository;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

@Component
public class RepairWorkRepositorySQL extends SqlAnnotationDrivenRepository<RepairWork> implements RepairWorkRepository {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RepairWorkRepositorySQL.class);

    @Autowire
    public RepairWorkRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper, SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, RepairWork.class);
    }


    @Override
    public List<RepairWork> findByKeyName(String key) {
        return template.query(queryGenerator.selectQuery(entitySqlDefinition) + " WHERE key_name = ?", pss -> pss.setString(1, key), (rs, rowNum) -> entityMapper.map(rs, entityClass));
    }

    @Override
    public List<RepairWork> findByCategoryKeyName(String key) {
        return template.query(queryGenerator.selectQuery(entitySqlDefinition, new SqlAliasTableNaming("rw")) + " JOIN repair_categories as r on rw.category_id = r.id WHERE r.key_name = ? ", pss -> pss.setString(1, key), (rs, rowNum) -> entityMapper.mapAs(rs, entityClass,"rw"));
    }

    @Override
    public Optional<RepairWork> findByKeyNameAndCategory_KeyName(String workKey, String categoryKey) {
        return template.query(queryGenerator.selectQuery(entitySqlDefinition, new SqlAliasTableNaming("rw")) + " JOIN repair_categories as r on rw.category_id = r.id WHERE rw.key_name = ? and r.key_name = ? ", pss -> {
            pss.setString(1, workKey);
            pss.setString(2, categoryKey);
        }, wrapToOptional((rs, rowNum) -> entityMapper.mapAs(rs, entityClass,"rw")));
    }

    @Override
    public Optional<RepairWorkLocalPart> findLocalByWork_IdAndLanguage_Lang(Long workId, String lang) {
        return template.query(queryGenerator.selectQuery(RepairWorkLocalPart.class, new SqlAliasTableNaming("lp")) + " LEFT JOIN app_locales as l ON lp.language_id = l.id where work_id = ? and l.lang = ?", pss -> {
            pss.setLong(1, workId);
            pss.setString(2, lang);
        }, wrapToOptional((rs, rowNum) -> entityMapper.mapAs(rs, RepairWorkLocalPart.class,"lp")));
    }

    @Override
    public Optional<RepairWorkPrice> findPriceByWork_IdAndCurrency_Code(Long workId, String code) {
        return template.query(queryGenerator.selectQuery(RepairWorkPrice.class, new SqlAliasTableNaming("lp")) + " LEFT JOIN app_currencies as c ON lp.currency_id = c.id where work_id = ? and c.code = ?", pss -> {
            pss.setLong(1, workId);
            pss.setString(2, code);
        }, wrapToOptional((rs, rowNum) -> entityMapper.mapAs(rs, RepairWorkPrice.class,"lp")));
    }
}
