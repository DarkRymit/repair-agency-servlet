package com.epam.finalproject.framework.security.support;

import com.epam.finalproject.framework.security.Authentication;

import java.io.Serializable;
import java.util.Objects;

public class SecurityContext implements Serializable {
    private Authentication authentication;

    public SecurityContext() {
    }

    public SecurityContext(Authentication authentication) {
        this.authentication = authentication;
    }


    public Authentication getAuthentication() {
        return this.authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecurityContext that = (SecurityContext) o;

        return Objects.equals(authentication, that.authentication);
    }

    @Override
    public int hashCode() {
        return authentication != null ? authentication.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SecurityContext{" +
                "authentication=" + authentication +
                '}';
    }
}