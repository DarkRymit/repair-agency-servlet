package com.epam.finalproject.model.search;

import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;

import java.util.Objects;
import java.util.Set;

public final class ReceiptSearch {
    private final PageRequest pageRequest;
    private final Set<ReceiptStatusEnum> receiptStatuses;
    private final String masterUsername;
    private final String customerUsername;

    public ReceiptSearch(PageRequest pageRequest, Set<ReceiptStatusEnum> receiptStatuses, String masterUsername,
            String customerUsername) {
        this.pageRequest = pageRequest;
        this.receiptStatuses = receiptStatuses;
        this.masterUsername = masterUsername;
        this.customerUsername = customerUsername;
    }

    public static ReceiptSearchBuilder builder() {
        return new ReceiptSearchBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptSearch that = (ReceiptSearch) o;
        return Objects.equals(pageRequest, that.pageRequest) && Objects.equals(receiptStatuses,
                that.receiptStatuses) && Objects.equals(masterUsername,
                that.masterUsername) && Objects.equals(customerUsername, that.customerUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageRequest, receiptStatuses, masterUsername, customerUsername);
    }

    @Override
    public String toString() {
        return "ReceiptSearch{" +
                "pageRequest=" + pageRequest +
                ", receiptStatuses=" + receiptStatuses +
                ", masterUsername='" + masterUsername + '\'' +
                ", customerUsername='" + customerUsername + '\'' +
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

    public String getCustomerUsername() {
        return this.customerUsername;
    }

    public static class ReceiptSearchBuilder {
        private PageRequest pageRequest;
        private Set<ReceiptStatusEnum> receiptStatuses;
        private String masterUsername;
        private String customerUsername;

        ReceiptSearchBuilder() {
        }

        public ReceiptSearchBuilder pageRequest(PageRequest pageRequest) {
            this.pageRequest = pageRequest;
            return this;
        }

        public ReceiptSearchBuilder receiptStatuses(Set<ReceiptStatusEnum> receiptStatuses) {
            this.receiptStatuses = receiptStatuses;
            return this;
        }

        public ReceiptSearchBuilder masterUsername(String masterUsername) {
            this.masterUsername = masterUsername;
            return this;
        }

        public ReceiptSearchBuilder customerUsername(String customerUsername) {
            this.customerUsername = customerUsername;
            return this;
        }

        public ReceiptSearch build() {
            return new ReceiptSearch(pageRequest, receiptStatuses, masterUsername, customerUsername);
        }

        public String toString() {
            return "ReceiptSearch.ReceiptSearchBuilder(pageRequest=" + this.pageRequest + ", receiptStatuses=" + this.receiptStatuses + ", masterUsername=" + this.masterUsername + ", customerUsername=" + this.customerUsername + ")";
        }
    }
}
