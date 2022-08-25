package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlReferenceId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.util.Objects;
import java.util.Set;


@SqlTable("repair_works")
public class RepairWork {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlReferenceId
    @SqlColumn("category_id")
    private RepairCategory category;

    @SqlColumn("key_name")
    private String keyName;

    private Set<RepairWorkLocalPart> localParts;

    private Set<RepairWorkPrice> prices;

    public RepairWork(Long id, RepairCategory category, String keyName, Set<RepairWorkLocalPart> localParts,
            Set<RepairWorkPrice> prices) {
        this.id = id;
        this.category = category;
        this.keyName = keyName;
        this.localParts = localParts;
        this.prices = prices;
    }

    public RepairWork() {
    }

    public static RepairWorkBuilder builder() {
        return new RepairWorkBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public RepairCategory getCategory() {
        return this.category;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public Set<RepairWorkLocalPart> getLocalParts() {
        return this.localParts;
    }

    public Set<RepairWorkPrice> getPrices() {
        return this.prices;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(RepairCategory category) {
        this.category = category;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public void setLocalParts(Set<RepairWorkLocalPart> localParts) {
        this.localParts = localParts;
    }

    public void setPrices(Set<RepairWorkPrice> prices) {
        this.prices = prices;
    }

    public static class RepairWorkBuilder {

        private Long id;
        private RepairCategory category;
        private String keyName;
        private Set<RepairWorkLocalPart> localParts;
        private Set<RepairWorkPrice> prices;
        RepairWorkBuilder() {
        }

        public RepairWorkBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RepairWorkBuilder category(RepairCategory category) {
            this.category = category;
            return this;
        }

        public RepairWorkBuilder keyName(String keyName) {
            this.keyName = keyName;
            return this;
        }

        public RepairWorkBuilder localParts(Set<RepairWorkLocalPart> localParts) {
            this.localParts = localParts;
            return this;
        }

        public RepairWorkBuilder prices(Set<RepairWorkPrice> prices) {
            this.prices = prices;
            return this;
        }

        public RepairWork build() {
            return new RepairWork(id, category, keyName, localParts, prices);
        }

        public String toString() {
            return "RepairWork.RepairWorkBuilder(id=" + this.id + ", category=" + this.category + ", keyName=" + this.keyName + ", localParts=" + this.localParts + ", prices=" + this.prices + ")";
        }

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepairWork that = (RepairWork) o;
        return Objects.equals(id, that.id)  && Objects.equals(keyName, that.keyName) && Objects.equals(localParts,
                that.localParts) && Objects.equals(prices, that.prices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyName, localParts, prices);
    }

    @Override
    public String toString() {
        return "RepairWork{" +
                "id=" + id +
                ", keyName='" + keyName + '\'' +
                ", localParts=" + localParts +
                ", prices=" + prices +
                '}';
    }
}
