package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;
import com.epam.finalproject.model.entity.enums.RoleEnum;

import java.io.Serializable;
import java.util.Objects;


@SqlTable("roles")
public class Role implements Serializable {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlColumn("name")
    private RoleEnum name;

    public Role(final RoleEnum name) {
        super();
        this.name = name;
    }

    public Role(Long id, RoleEnum name) {
        this.id = id;
        this.name = name;
    }

    public Role() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id) && name == role.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public RoleEnum getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
}