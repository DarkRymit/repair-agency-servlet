package com.epam.finalproject.model.search;

import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;

import java.util.Objects;
import java.util.Set;

public final class ReceiptWithCustomerSearch {
    private final PageRequest pageRequest;
    private final Set<ReceiptStatusEnum> receiptStatuses;
    private final String masterUsername;
    private final User customer;

    public ReceiptWithCustomerSearch(PageRequest pageRequest, Set<ReceiptStatusEnum> receiptStatuses,
            String masterUsername, User customer) {
        this.pageRequest = pageRequest;
        this.receiptStatuses = receiptStatuses;
        this.masterUsername = masterUsername;
        this.customer = customer;
    }

    public static ReceiptWithCustomerSearchBuilder builder() {
        return new ReceiptWithCustomerSearchBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptWithCustomerSearch that = (ReceiptWithCustomerSearch) o;
        return Objects.equals(pageRequest, that.pageRequest) && Objects.equals(receiptStatuses,
                that.receiptStatuses) && Objects.equals(masterUsername,
                that.masterUsername) && Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageRequest, receiptStatuses, masterUsername, customer);
    }

    @Override
    public String toString() {
        return "ReceiptWithCustomerSearch{" +
                "pageRequest=" + pageRequest +
                ", receiptStatuses=" + receiptStatuses +
                ", masterUsername='" + masterUsername + '\'' +
                ", customer=" + customer +
                '}';
    }

    public PageRequest getPageRequest() {
        return this.pageRequest;
    }

    public Set<ReceiptStatusEnum> getReceiptStatuses() {
        return this.receiptStatuses;
    }

    public String getMasterUsername() {
        return this.masterUsername;
    }

    public User getCustomer() {
        return this.customer;
    }

    public static class ReceiptWithCustomerSearchBuilder {
        private PageRequest pageRequest;
        private Set<ReceiptStatusEnum> receiptStatuses;
        private String masterUsername;
        private User customer;

        ReceiptWithCustomerSearchBuilder() {
        }

        public ReceiptWithCustomerSearchBuilder pageRequest(PageRequest pageRequest) {
            this.pageRequest = pageRequest;
            return this;
        }

        public ReceiptWithCustomerSearchBuilder receiptStatuses(
                Set<ReceiptStatusEnum> receiptStatuses) {
            this.receiptStatuses = receiptStatuses;
            return this;
        }

        public ReceiptWithCustomerSearchBuilder masterUsername(String masterUsername) {
            this.masterUsername = masterUsername;
            return this;
        }

        public ReceiptWithCustomerSearchBuilder customer(User customer) {
            this.customer = customer;
            return this;
        }

        public ReceiptWithCustomerSearch build() {
            return new ReceiptWithCustomerSearch(pageRequest, receiptStatuses, masterUsername, customer);
        }

        public String toString() {
            return "ReceiptWithCustomerSearch.ReceiptWithCustomerSearchBuilder(pageRequest=" + this.pageRequest + ", receiptStatuses=" + this.receiptStatuses + ", masterUsername=" + this.masterUsername + ", customer=" + this.customer + ")";
        }
    }
}
