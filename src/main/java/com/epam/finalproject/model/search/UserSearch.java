package com.epam.finalproject.model.search;

import com.epam.finalproject.framework.data.PageRequest;

import java.util.Objects;

public final class UserSearch {
    private final PageRequest pageRequest;
    private final String username;

    public UserSearch(PageRequest pageRequest, String username) {
        this.pageRequest = pageRequest;
        this.username = username;
    }

    public static UserSearchBuilder builder() {
        return new UserSearchBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserSearch that = (UserSearch) o;
        return Objects.equals(pageRequest, that.pageRequest) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageRequest, username);
    }

    @Override
    public String toString() {
        return "UserSearch{" +
                "pageRequest=" + pageRequest +
                ", username='" + username + '\'' +
                '}';
    }

    public PageRequest getPageRequest() {
        return this.pageRequest;
    }

    public String getUsername() {
        return this.username;
    }

    public static class UserSearchBuilder {
        private PageRequest pageRequest;
        private String username;

        UserSearchBuilder() {
        }

        public UserSearchBuilder pageRequest(PageRequest pageRequest) {
            this.pageRequest = pageRequest;
            return this;
        }

        public UserSearchBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserSearch build() {
            return new UserSearch(pageRequest, username);
        }

        public String toString() {
            return "UserSearch.UserSearchBuilder(pageRequest=" + this.pageRequest + ", username=" + this.username + ")";
        }
    }
}
