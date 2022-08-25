package com.epam.finalproject.framework.security.web;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.security.Authentication;
import com.epam.finalproject.framework.security.support.SecurityContext;
import com.epam.finalproject.framework.security.support.SecurityContextHolder;
import com.epam.finalproject.framework.web.WebHttpPair;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class HttpSessionSecurityContextRepository implements SecurityContextRepository {
    public static final String SECURITY_CONTEXT_KEY = "SECURITY_CONTEXT";

    public SecurityContext loadContext(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        SecurityContext context = this.readSecurityContextFromSession(httpSession);
        if (context == null) {
            context = this.generateNewContext();
        }
        return context;
    }

    public void saveContext(SecurityContext context, WebHttpPair pair) {
        HttpSession httpSession = pair.getRequest().getSession();
        Authentication authentication = context.getAuthentication();
        if (authentication != null){
            httpSession.setAttribute(SECURITY_CONTEXT_KEY,context);
        }else {
            httpSession.removeAttribute(SECURITY_CONTEXT_KEY);
        }
    }

    public boolean containsContext(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        } else {
            return session.getAttribute(SECURITY_CONTEXT_KEY) != null;
        }
    }

    private SecurityContext readSecurityContextFromSession(HttpSession httpSession) {
        if (httpSession == null) {
            return null;
        } else {
            return (SecurityContext) httpSession.getAttribute(SECURITY_CONTEXT_KEY);
        }
    }

    protected SecurityContext generateNewContext() {
        return SecurityContextHolder.createEmptyContext();
    }

}