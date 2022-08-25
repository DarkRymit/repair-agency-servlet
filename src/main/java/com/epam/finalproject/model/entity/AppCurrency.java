package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.io.Serializable;
import java.util.Objects;


@SqlTable("app_currencies")
public class AppCurrency implements Serializable {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlColumn("code")
    private String code;

    public AppCurrency(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public AppCurrency() {
    }

    public Long getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppCurrency currency = (AppCurrency) o;
        return Objects.equals(id, currency.id) && Objects.equals(code, currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }

    @Override
    public String toString() {
        return "AppCurrency{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
