package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.SqlAnnotationDrivenRepository;
import com.epam.finalproject.framework.data.sql.mapping.SqlAliasTableNaming;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.RepairCategory;
import com.epam.finalproject.model.entity.RepairCategoryLocalPart;
import com.epam.finalproject.model.entity.RepairWork;
import com.epam.finalproject.repository.RepairCategoryRepository;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.epam.finalproject.repository.impl.SqlAliasConstants.*;

@Component
public class RepairCategoryRepositorySQL extends SqlAnnotationDrivenRepository<RepairCategory>
        implements RepairCategoryRepository {


    public static final String FIND_BY_KEY_NAME_SQL = "SELECT " + REPAIR_CATEGORY_ALIAS + "," + REPAIR_WORK_ALIAS
            + "FROM repair_categories AS rc LEFT JOIN repair_works AS rw ON rw.category_id = rc.id WHERE rc.key_name = ?";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RepairCategoryRepositorySQL.class);

    @Autowire
    public RepairCategoryRepositorySQL(JdbcTemplate template, SqlEntityMapper entityMapper,
            SqlEntityQueryGenerator queryGenerator, AnnotationSqlDefinitionReader definitionReader) {
        super(template, definitionReader, entityMapper, queryGenerator, RepairCategory.class);
    }


    @Override
    public List<RepairCategory> findAll(Sort sort) {
        List<RepairCategory> categories = super.findAll(sort);
        for (RepairCategory category : categories) {
            List<RepairWork> list = template.query(
                    queryGenerator.selectQuery(RepairWork.class) + " where category_id = " + category.getId(),
                    (rs, rowNum) -> entityMapper.map(rs, RepairWork.class));
            category.setWorks(new HashSet<>(list));
        }
        log.trace("Categories {}", categories);
        return categories;
    }


    @Override
    public Optional<RepairCategory> findByKeyName(String key) {
        final RepairCategory[] category = {null};
        template.query(FIND_BY_KEY_NAME_SQL, pss -> pss.setString(1, key), rs -> {
            if (category[0] == null) {
                category[0] = entityMapper.mapAs(rs, entityClass, "rc");
                category[0].setWorks(new HashSet<>());
            }
            category[0].getWorks().add(entityMapper.mapAs(rs, RepairWork.class, "rw"));
        });
        return Optional.of(category[0]);
    }

    @Override
    public Optional<RepairCategoryLocalPart> findFirstLocalPartByCategory_IdAndLanguage_Lang(Long categoryId,
            String lang) {
        log.trace("Lang {}", lang);
        return template.query(queryGenerator.selectQuery(RepairCategoryLocalPart.class, new SqlAliasTableNaming(
                        "lp")) + " LEFT JOIN app_locales as l on lp.language_id = l.id where lp.category_id = ? and l.lang = ?",
                pss -> {
                    pss.setLong(1, categoryId);
                    pss.setString(2, lang);
                }, wrapToOptional((rs, row) -> entityMapper.mapAs(rs, RepairCategoryLocalPart.class, "lp")));
    }
}
