package com.epam.finalproject.framework.security.support;

import com.epam.finalproject.framework.security.GrantedAuthority;

import java.util.Objects;

public final class SimpleGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 560L;
    private final String role;

    public SimpleGrantedAuthority(String role) {
        this.role = role;
    }

    public String getAuthority() {
        return this.role;
    }


    @Override
    public int hashCode() {
        return this.role.hashCode();
    }

    @Override
    public String toString() {
        return this.role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleGrantedAuthority that = (SimpleGrantedAuthority) o;
        return Objects.equals(role, that.role);
    }
}
