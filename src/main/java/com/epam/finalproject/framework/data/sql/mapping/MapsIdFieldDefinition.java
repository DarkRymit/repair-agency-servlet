package com.epam.finalproject.framework.data.sql.mapping;


import java.util.Objects;

public class MapsIdFieldDefinition extends ReferenceIdFieldDefinition {

    private boolean sameAsIdName;

    public MapsIdFieldDefinition() {
    }

    public MapsIdFieldDefinition(Class<?> clazz, String fieldName, Class<?> fieldClass, String columnName, String columnDefinition, boolean entity) {
        super(clazz, fieldName, fieldClass, columnName, columnDefinition, entity);
    }

    public boolean isSameAsIdName() {
        return sameAsIdName;
    }

    public void setSameAsIdName(boolean sameAsIdName) {
        this.sameAsIdName = sameAsIdName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MapsIdFieldDefinition that = (MapsIdFieldDefinition) o;
        return sameAsIdName == that.sameAsIdName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sameAsIdName);
    }
}
