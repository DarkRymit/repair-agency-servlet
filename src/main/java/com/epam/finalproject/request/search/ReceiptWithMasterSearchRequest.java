package com.epam.finalproject.request.search;

import java.util.Objects;
import java.util.Set;

public class ReceiptWithMasterSearchRequest {
    String sort;
    Set<String> status;
    String customer;
    Integer page;
    Integer count;

    public ReceiptWithMasterSearchRequest(String sort, Set<String> status, String customer, Integer page,
            Integer count) {
        this.sort = sort;
        this.status = status;
        this.customer = customer;
        this.page = page;
        this.count = count;
    }

    public ReceiptWithMasterSearchRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptWithMasterSearchRequest that = (ReceiptWithMasterSearchRequest) o;
        return Objects.equals(sort, that.sort) && Objects.equals(status,
                that.status) && Objects.equals(customer, that.customer) && Objects.equals(page,
                that.page) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sort, status, customer, page, count);
    }

    @Override
    public String toString() {
        return "ReceiptWithMasterSearchRequest{" +
                "sort='" + sort + '\'' +
                ", status=" + status +
                ", customer='" + customer + '\'' +
                ", page=" + page +
                ", count=" + count +
                '}';
    }

    public String getSort() {
        return this.sort;
    }

    public Set<String> getStatus() {
        return this.status;
    }

    public String getCustomer() {
        return this.customer;
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setStatus(Set<String> status) {
        this.status = status;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
