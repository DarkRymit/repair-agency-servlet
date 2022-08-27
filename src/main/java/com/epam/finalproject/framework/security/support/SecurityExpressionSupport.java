package com.epam.finalproject.framework.security.support;

import com.epam.finalproject.framework.security.Authentication;
import com.epam.finalproject.framework.security.util.SecurityUtils;

public class SecurityExpressionSupport {
    private final SecurityContext securityContext;
    private final Authentication authentication;

    public SecurityExpressionSupport(SecurityContext securityContext) {
        this.securityContext = securityContext;
        this.authentication = securityContext.getAuthentication();
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public boolean hasRole(String role) {
        return SecurityUtils.hasRole(authentication,role);
    }

    public boolean isAnonymous() {
        return SecurityUtils.isAnonymous(authentication);
    }


    public boolean isAuthenticated() {
        return SecurityUtils.isAuthenticated(authentication);
    }
}
