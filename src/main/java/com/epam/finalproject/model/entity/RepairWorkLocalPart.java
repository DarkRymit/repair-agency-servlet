package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlReferenceId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.util.Objects;


@SqlTable("repair_work_local_parts")
public class RepairWorkLocalPart {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlColumn("work_id")
    private Long workId;

    @SqlColumn("name")
    private String name;

    @SqlReferenceId
    @SqlColumn("language_id")
    private AppLocale language;

    public RepairWorkLocalPart(Long id, Long workId, String name, AppLocale language) {
        this.id = id;
        this.workId = workId;
        this.name = name;
        this.language = language;
    }

    public RepairWorkLocalPart() {
    }

    public Long getId() {
        return this.id;
    }

    public Long getWorkId() {
        return this.workId;
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

    public void setWorkId(Long workId) {
        this.workId = workId;
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
        RepairWorkLocalPart localPart = (RepairWorkLocalPart) o;
        return Objects.equals(id, localPart.id) && Objects.equals(workId,
                localPart.workId) && Objects.equals(name, localPart.name) && Objects.equals(language,
                localPart.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workId, name, language);
    }

    @Override
    public String toString() {
        return "RepairWorkLocalPart{" +
                "id=" + id +
                ", workId=" + workId +
                ", name='" + name + '\'' +
                ", language=" + language +
                '}';
    }
}
