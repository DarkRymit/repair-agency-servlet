package com.epam.finalproject.request.search;

import com.epam.finalproject.framework.web.resolver.annotation.CollectionType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;
import java.util.Set;

public class ReceiptSearchRequest {

    String sort;

    @CollectionType(String.class)
    Set<String> status;

    String customer;

    String master;
    @PositiveOrZero
    Integer page;
    @Positive
    Integer count;

    public ReceiptSearchRequest(String sort, Set<String> status, String customer, String master, Integer page,
            Integer count) {
        this.sort = sort;
        this.status = status;
        this.customer = customer;
        this.master = master;
        this.page = page;
        this.count = count;
    }

    public ReceiptSearchRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptSearchRequest that = (ReceiptSearchRequest) o;
        return Objects.equals(sort, that.sort) && Objects.equals(status,
                that.status) && Objects.equals(customer, that.customer) && Objects.equals(master,
                that.master) && Objects.equals(page, that.page) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sort, status, customer, master, page, count);
    }

    @Override
    public String toString() {
        return "ReceiptSearchRequest{" +
                "sort='" + sort + '\'' +
                ", status=" + status +
                ", customer='" + customer + '\'' +
                ", master='" + master + '\'' +
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

    public String getMaster() {
        return this.master;
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

    public void setMaster(String master) {
        this.master = master;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
