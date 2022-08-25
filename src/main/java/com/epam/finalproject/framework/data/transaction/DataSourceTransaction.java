package com.epam.finalproject.framework.data.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class DataSourceTransaction {

    private final Connection target;


    public DataSourceTransaction(Connection target) {
        Objects.requireNonNull(target);
        this.target = target;
    }


    public Connection getTarget() {
        return target;
    }


    public void commit() {
        try {
            target.commit();
        } catch (SQLException e) {
            throw new TransactionException(String.format("Cant commit for target %s",target),e);
        }
    }


    public void rollback() {
        try {
            target.rollback();
        } catch (SQLException e) {
            throw new TransactionException(String.format("Cant rollback for target %s",target),e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataSourceTransaction that = (DataSourceTransaction) o;
        return Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target);
    }

    @Override
    public String toString() {
        return "DataSourceTransaction{" +
                "target=" + target +
                '}';
    }
}
