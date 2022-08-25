package com.epam.finalproject.framework.security.web;

import com.epam.finalproject.framework.security.support.SecurityContext;
import com.epam.finalproject.framework.web.WebHttpPair;
import jakarta.servlet.http.HttpServletRequest;

public interface SecurityContextRepository {

    SecurityContext loadContext(HttpServletRequest request);

    void saveContext(SecurityContext context, WebHttpPair pair);

    boolean containsContext(HttpServletRequest request);
}
