package com.epam.finalproject.framework.security.authentication;

import com.epam.finalproject.framework.security.Authentication;
import com.epam.finalproject.framework.security.GrantedAuthority;
import com.epam.finalproject.framework.security.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

public class UsernamePasswordAuthenticationToken implements Authentication {
    private static final long serialVersionUID = 560L;
    private final Object principal;
    private final Collection<GrantedAuthority> authorities;

    public UsernamePasswordAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null) {
            this.authorities = List.of();
        } else {

            this.authorities = List.copyOf(authorities);
        }
        this.principal = principal;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        if (this.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) this.getPrincipal()).getUsername();
        } else if (this.getPrincipal() instanceof Principal) {
            return ((Principal) this.getPrincipal()).getName();
        } else {
            return this.getPrincipal() == null ? "" : this.getPrincipal().toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) o;

        if (principal != null ? !principal.equals(token.principal) : token.principal != null) return false;
        return authorities != null ? authorities.equals(token.authorities) : token.authorities == null;
    }

    @Override
    public int hashCode() {
        int result = principal != null ? principal.hashCode() : 0;
        result = 31 * result + (authorities != null ? authorities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UsernamePasswordAuthenticationToken{" +
                "principal=" + principal +
                ", authorities=" + authorities +
                '}';
    }
}
