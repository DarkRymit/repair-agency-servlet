package com.epam.finalproject.framework.beans.factory.config;

import java.util.Objects;

public class PropertyValueHolder {
    private final String name;

    private final InjectPropertyRule rule;

    private final boolean methodName;

    private Object value;

    public PropertyValueHolder(String targetName, InjectPropertyRule rule, boolean methodName, Object value) {
        this.name = targetName;
        this.rule = rule;
        this.methodName = methodName;
        this.value = value;
    }

    public PropertyValueHolder(String name, InjectPropertyRule rule, Object value) {
        this.name = name;
        this.rule = rule;
        this.value = value;
        this.methodName = false;
    }

    public PropertyValueHolder(String name, boolean methodName, Object value) {
        this.name = name;
        this.rule = InjectPropertyRule.SETTER_FIELD;
        this.value = value;
        this.methodName = methodName;
    }

    public PropertyValueHolder(String targetName, Object value) {
        this.name = targetName;
        this.value = value;
        this.methodName = false;
        this.rule = InjectPropertyRule.SETTER_FIELD;
    }

    public String getName() {
        return name;
    }

    public InjectPropertyRule getRule() {
        return rule;
    }

    public boolean isMethodName() {
        return methodName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PropertyValueHolder that = (PropertyValueHolder) o;

        if (methodName != that.methodName) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (rule != that.rule) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,rule,methodName,value);
    }
}
