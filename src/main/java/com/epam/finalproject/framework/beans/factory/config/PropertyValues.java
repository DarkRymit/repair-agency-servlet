package com.epam.finalproject.framework.beans.factory.config;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PropertyValues {
    private final Set<PropertyValueHolder> constructorValueHolders;

    public PropertyValues(Set<PropertyValueHolder> propertyValueList) {
        this.constructorValueHolders = propertyValueList;
    }

    public PropertyValues() {
        this.constructorValueHolders = new HashSet<>();
    }

    public Set<PropertyValueHolder> getValueHolders() {
        return constructorValueHolders;
    }

    public PropertyValues add(String propertyName, Object propertyValue) {
        this.add(new PropertyValueHolder(propertyName, propertyValue));
        return this;
    }

    public PropertyValues add(PropertyValueHolder newValue) {
        if(constructorValueHolders.stream().anyMatch(valueHolder -> valueHolder.getName().equals(newValue.getName()))){
            throw new IllegalArgumentException("Component already used");
        }
        constructorValueHolders.add(newValue);
        return this;
    }

    public boolean isEmpty() {
       return constructorValueHolders.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PropertyValues that = (PropertyValues) o;
        return Objects.equals(constructorValueHolders, that.constructorValueHolders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constructorValueHolders);
    }

    @Override
    public String toString() {
        return "PropertyValues{" +
                "constructorValueHolders=" + constructorValueHolders +
                '}';
    }
}
