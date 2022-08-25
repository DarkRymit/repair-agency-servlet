package com.epam.finalproject.framework.beans.factory.config;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ConstructArguments {
    private final List<ConstructValueHolder> argumentValues;

    public ConstructArguments() {
        this.argumentValues = new LinkedList<>();
    }

    public ConstructArguments add(ConstructValueHolder newValue) {
        argumentValues.add(newValue);
        return this;
    }

    public List<ConstructValueHolder> getArgumentValues() {
        return Collections.unmodifiableList(this.argumentValues);
    }

    public ConstructValueHolder get(int index) {
        return argumentValues.get(index);
    }

    public boolean isEmpty() {
        return argumentValues.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConstructArguments that = (ConstructArguments) o;
        return Objects.equals(argumentValues, that.argumentValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(argumentValues);
    }

    @Override
    public String toString() {
        return "ConstructArguments{" +
                "argumentValues=" + argumentValues +
                '}';
    }
}
