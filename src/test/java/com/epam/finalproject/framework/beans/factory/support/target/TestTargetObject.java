package com.epam.finalproject.framework.beans.factory.support.target;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.beans.annotation.Value;

import java.util.Objects;

@Component("testObj")
public class TestTargetObject {
    Integer id;
    String name;

    @Value("55")
    int setterField;

    String injectField;

    public TestTargetObject() {
    }

    public TestTargetObject(@Value("5") Integer id,@Value("Five") String name) {
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

    public int getSetterField() {
        return setterField;
    }

    public void setSetterField(int setterField) {
        this.setterField = setterField;
    }

    public String getInjectField() {
        return injectField;
    }

    public void setInjectField(String injectField) {
        this.injectField = injectField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestTargetObject that = (TestTargetObject) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
