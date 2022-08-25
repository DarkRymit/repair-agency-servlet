package com.epam.finalproject.model.search;

import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;

import java.util.Objects;
import java.util.Set;

public final class ReceiptWithMasterSearch {
    private final PageRequest pageRequest;
    private final Set<ReceiptStatusEnum> receiptStatuses;
    private final String customerUsername;
    private final User master;

    public ReceiptWithMasterSearch(PageRequest pageRequest, Set<ReceiptStatusEnum> receiptStatuses,
            String customerUsername, User master) {
        this.pageRequest = pageRequest;
        this.receiptStatuses = receiptStatuses;
        this.customerUsername = customerUsername;
        this.master = master;
    }

    public static ReceiptWithMasterSearchBuilder builder() {
        return new ReceiptWithMasterSearchBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptWithMasterSearch that = (ReceiptWithMasterSearch) o;
        return Objects.equals(pageRequest, that.pageRequest) && Objects.equals(receiptStatuses,
                that.receiptStatuses) && Objects.equals(customerUsername,
                that.customerUsername) && Objects.equals(master, that.master);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageRequest, receiptStatuses, customerUsername, master);
    }

    @Override
    public String toString() {
        return "ReceiptWithMasterSearch{" +
                "pageRequest=" + pageRequest +
                ", receiptStatuses=" + receiptStatuses +
                ", customerUsername='" + customerUsername + '\'' +
                ", master=" + master +
                '}';
    }

    public PageRequest getPageRequest() {
        return this.pageRequest;
    }

    public Set<ReceiptStatusEnum> getReceiptStatuses() {
        return this.receiptStatuses;
    }

    public String getCustomerUsername() {
        return this.customerUsername;
    }

    public User getMaster() {
        return this.master;
    }

    public static class ReceiptWithMasterSearchBuilder {
        private PageRequest pageRequest;
        private Set<ReceiptStatusEnum> receiptStatuses;
        private String customerUsername;
        private User master;

        ReceiptWithMasterSearchBuilder() {
        }

        public ReceiptWithMasterSearchBuilder pageRequest(PageRequest pageRequest) {
            this.pageRequest = pageRequest;
            return this;
        }

        public ReceiptWithMasterSearchBuilder receiptStatuses(
                Set<ReceiptStatusEnum> receiptStatuses) {
            this.receiptStatuses = receiptStatuses;
            return this;
        }

        public ReceiptWithMasterSearchBuilder customerUsername(String customerUsername) {
            this.customerUsername = customerUsername;
            return this;
        }

        public ReceiptWithMasterSearchBuilder master(User master) {
            this.master = master;
            return this;
        }

        public ReceiptWithMasterSearch build() {
            return new ReceiptWithMasterSearch(pageRequest, receiptStatuses, customerUsername, master);
        }

        public String toString() {
            return "ReceiptWithMasterSearch.ReceiptWithMasterSearchBuilder(pageRequest=" + this.pageRequest + ", receiptStatuses=" + this.receiptStatuses + ", customerUsername=" + this.customerUsername + ", master=" + this.master + ")";
        }
    }
}
