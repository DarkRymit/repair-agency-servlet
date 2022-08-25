package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;

import java.util.Objects;


@SqlTable("receipt_statuses")
public class ReceiptStatus {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlColumn("name")
    private ReceiptStatusEnum name;

    public ReceiptStatus(Long id, ReceiptStatusEnum name) {
        this.id = id;
        this.name = name;
    }

    public ReceiptStatus() {
    }

    public Long getId() {
        return this.id;
    }

    public ReceiptStatusEnum getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(ReceiptStatusEnum name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptStatus that = (ReceiptStatus) o;
        return Objects.equals(id, that.id) && name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ReceiptStatus{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
