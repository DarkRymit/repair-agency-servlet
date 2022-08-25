package com.epam.finalproject.framework.data.sql.mapping;

import java.util.Objects;

public class ReferenceIdFieldDefinition extends SimpleFieldDefinition {

    private boolean entity;

    public ReferenceIdFieldDefinition(Class<?> clazz, String fieldName, Class<?> fieldClass, String columnName, String columnDefinition, boolean entity) {
        super(clazz, fieldName, fieldClass, columnName, columnDefinition);
        this.entity = entity;
    }

    public boolean isEntity() {
        return entity;
    }

    public void setEntity(boolean entity) {
        this.entity = entity;
    }

    public ReferenceIdFieldDefinition() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReferenceIdFieldDefinition that = (ReferenceIdFieldDefinition) o;
        return entity == that.entity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), entity);
    }
}
