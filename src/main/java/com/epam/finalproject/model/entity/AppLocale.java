package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.io.Serializable;
import java.util.Objects;


@SqlTable("app_locales")
public class AppLocale  implements Serializable {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlColumn("lang")
    private String lang;

    public AppLocale(Long id, String lang) {
        this.id = id;
        this.lang = lang;
    }

    public AppLocale() {
    }

    public Long getId() {
        return this.id;
    }

    public String getLang() {
        return this.lang;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppLocale appLocale = (AppLocale) o;
        return Objects.equals(id, appLocale.id) && Objects.equals(lang, appLocale.lang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lang);
    }

    @Override
    public String toString() {
        return "AppLocale{" +
                "id=" + id +
                ", lang='" + lang + '\'' +
                '}';
    }
}
