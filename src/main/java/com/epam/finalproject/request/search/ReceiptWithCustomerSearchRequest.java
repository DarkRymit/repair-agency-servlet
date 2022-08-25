package com.epam.finalproject.request.search;

import com.epam.finalproject.framework.web.resolver.annotation.CollectionType;

import java.util.Objects;
import java.util.Set;

public class ReceiptWithCustomerSearchRequest {

    String sort;

    @CollectionType(String.class)
    Set<String> status;

    String master;

    Integer page;

    Integer count;

    public ReceiptWithCustomerSearchRequest(String sort, Set<String> status, String master, Integer page,
            Integer count) {
        this.sort = sort;
        this.status = status;
        this.master = master;
        this.page = page;
        this.count = count;
    }

    public ReceiptWithCustomerSearchRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptWithCustomerSearchRequest that = (ReceiptWithCustomerSearchRequest) o;
        return Objects.equals(sort, that.sort) && Objects.equals(status,
                that.status) && Objects.equals(master, that.master) && Objects.equals(page,
                that.page) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sort, status, master, page, count);
    }

    @Override
    public String toString() {
        return "ReceiptWithCustomerSearchRequest{" +
                "sort='" + sort + '\'' +
                ", status=" + status +
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
