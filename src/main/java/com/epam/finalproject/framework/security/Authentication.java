package com.epam.finalproject.framework.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

public interface Authentication extends Principal, Serializable {
    Collection<? extends GrantedAuthority> getAuthorities();

    Object getPrincipal();

    boolean isAuthenticated();
}