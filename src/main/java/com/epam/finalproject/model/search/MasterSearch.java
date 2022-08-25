package com.epam.finalproject.model.search;

import com.epam.finalproject.framework.data.PageRequest;

import java.util.Objects;

public final class MasterSearch {
    private final PageRequest pageRequest;
    private final String username;

    public MasterSearch(PageRequest pageRequest, String username) {
        this.pageRequest = pageRequest;
        this.username = username;
    }

    public static MasterSearchBuilder builder() {
        return new MasterSearchBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MasterSearch that = (MasterSearch) o;
        return Objects.equals(pageRequest, that.pageRequest) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageRequest, username);
    }

    @Override
    public String toString() {
        return "MasterSearch{" +
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

    public static class MasterSearchBuilder {
        private PageRequest pageRequest;
        private String username;

        MasterSearchBuilder() {
        }

        public MasterSearchBuilder pageRequest(PageRequest pageRequest) {
            this.pageRequest = pageRequest;
            return this;
        }

        public MasterSearchBuilder username(String username) {
            this.username = username;
            return this;
        }

        public MasterSearch build() {
            return new MasterSearch(pageRequest, username);
        }

        public String toString() {
            return "MasterSearch.MasterSearchBuilder(pageRequest=" + this.pageRequest + ", username=" + this.username + ")";
        }
    }
}
