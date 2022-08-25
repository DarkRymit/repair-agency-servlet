package com.epam.finalproject.request.search;

import java.util.Objects;

public class MasterSearchRequest {
    String sort;
    String username;
    Integer page;
    Integer count;

    public MasterSearchRequest(String sort, String username, Integer page, Integer count) {
        this.sort = sort;
        this.username = username;
        this.page = page;
        this.count = count;
    }

    public MasterSearchRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MasterSearchRequest that = (MasterSearchRequest) o;
        return Objects.equals(sort, that.sort) && Objects.equals(username,
                that.username) && Objects.equals(page, that.page) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sort, username, page, count);
    }

    @Override
    public String toString() {
        return "MasterSearchRequest{" +
                "sort='" + sort + '\'' +
                ", username='" + username + '\'' +
                ", page=" + page +
                ", count=" + count +
                '}';
    }

    public String getSort() {
        return this.sort;
    }

    public String getUsername() {
        return this.username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
