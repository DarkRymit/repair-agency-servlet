package com.epam.finalproject.request.search;

import com.epam.finalproject.framework.web.resolver.annotation.CollectionType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;
import java.util.Set;

public class UserSearchRequest {

    String sort;

    @CollectionType(String.class)
    Set<String> status;

    String username;

    @PositiveOrZero
    Integer page;

    @Positive
    Integer count;

    public UserSearchRequest(String sort, Set<String> status, String username, Integer page, Integer count) {
        this.sort = sort;
        this.status = status;
        this.username = username;
        this.page = page;
        this.count = count;
    }

    public UserSearchRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserSearchRequest that = (UserSearchRequest) o;
        return Objects.equals(sort, that.sort) && Objects.equals(status,
                that.status) && Objects.equals(username, that.username) && Objects.equals(page,
                that.page) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sort, status, username, page, count);
    }

    @Override
    public String toString() {
        return "UserSearchRequest{" +
                "sort='" + sort + '\'' +
                ", status=" + status +
                ", username='" + username + '\'' +
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

    public void setStatus(Set<String> status) {
        this.status = status;
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
