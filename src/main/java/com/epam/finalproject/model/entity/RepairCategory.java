package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@SqlTable("repair_categories")
public class RepairCategory {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlColumn("key_name")
    private String keyName;

    private Set<RepairWork> works = new HashSet<>();

    private Set<RepairCategoryLocalPart> localParts = new HashSet<>();

    public RepairCategory(Long id, String keyName, Set<RepairWork> works, Set<RepairCategoryLocalPart> localParts) {
        this.id = id;
        this.keyName = keyName;
        this.works = works;
        this.localParts = localParts;
    }

    public RepairCategory() {
    }

    public Long getId() {
        return this.id;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public Set<RepairWork> getWorks() {
        return this.works;
    }

    public Set<RepairCategoryLocalPart> getLocalParts() {
        return this.localParts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public void setWorks(Set<RepairWork> works) {
        this.works = works;
    }

    public void setLocalParts(Set<RepairCategoryLocalPart> localParts) {
        this.localParts = localParts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepairCategory category = (RepairCategory) o;
        return Objects.equals(id, category.id) && Objects.equals(keyName,
                category.keyName) && Objects.equals(works, category.works) && Objects.equals(localParts,
                category.localParts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyName, works, localParts);
    }

    @Override
    public String toString() {
        return "RepairCategory{" +
                "id=" + id +
                ", keyName='" + keyName + '\'' +
                ", works=" + works +
                ", localParts=" + localParts +
                '}';
    }
}
