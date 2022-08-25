package com.epam.finalproject.framework.beans.factory.support.target;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.beans.annotation.Value;

import java.util.Objects;

@Component
public class AloneTestTargetObject {
    Integer id;
    String name;

    String setterField;

    public AloneTestTargetObject() {
    }

    public AloneTestTargetObject(@Value("5") Integer id, @Value("Five") String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSetterField() {
        return setterField;
    }

    public void setSetterField(String setterField) {
        this.setterField = setterField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AloneTestTargetObject that = (AloneTestTargetObject) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
