package com.epam.finalproject.framework.data.transaction;

import java.util.Objects;

public class DefaultTransactionStatus implements TransactionStatus{

    private final Object transaction;

    private boolean completed = false;

    public DefaultTransactionStatus(Object transaction) {
        this.transaction = transaction;
    }

    @Override
    public Object getTransaction() {
        return transaction;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        this.completed = true;
    }

    @Override
    public String toString() {
        return "DefaultTransactionStatus{" +
                "transaction=" + transaction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultTransactionStatus that = (DefaultTransactionStatus) o;
        return Objects.equals(transaction, that.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction);
    }
}
