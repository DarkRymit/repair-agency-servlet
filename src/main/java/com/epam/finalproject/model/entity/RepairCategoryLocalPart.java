package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlReferenceId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.util.Objects;


@SqlTable("repair_category_local_parts")
public class RepairCategoryLocalPart {

    @SqlColumn("id")
    private Long id;

    @SqlReferenceId
    @SqlColumn("category_id")
    private RepairCategory category;

    @SqlColumn("name")
    private String name;

    @SqlReferenceId
    @SqlColumn("language_id")
    private AppLocale language;

    public RepairCategoryLocalPart(Long id, RepairCategory category, String name, AppLocale language) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.language = language;
    }

    public RepairCategoryLocalPart() {
    }

    public Long getId() {
        return this.id;
    }

    public RepairCategory getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }

    public AppLocale getLanguage() {
        return this.language;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(RepairCategory category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(AppLocale language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepairCategoryLocalPart localPart = (RepairCategoryLocalPart) o;
        return Objects.equals(id, localPart.id) && Objects.equals(name, localPart.name) && Objects.equals(language,
                localPart.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, language);
    }

    @Override
    public String toString() {
        return "RepairCategoryLocalPart{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", language=" + language +
                '}';
    }
}
