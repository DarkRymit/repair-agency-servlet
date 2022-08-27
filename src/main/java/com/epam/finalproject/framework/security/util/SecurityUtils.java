package com.epam.finalproject.framework.security.util;

import com.epam.finalproject.framework.security.Authentication;
import com.epam.finalproject.framework.security.GrantedAuthority;
import com.epam.finalproject.framework.security.UserDetails;
import com.epam.finalproject.framework.security.support.SecurityExpressionSupport;
import org.apache.commons.jexl3.*;

import java.util.HashMap;
import java.util.Map;

public interface SecurityUtils {


    static boolean hasRole(Authentication auth, String role) {
        return isAuthenticated(auth) && auth.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .equals("ROLE_" + role));
    }

    static boolean hasRole(UserDetails userDetails, String role) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(s -> s.equals("ROLE_" + role));
    }

    static boolean isAnonymous(Authentication auth) {
        return auth == null || !auth.isAuthenticated();
    }


    static boolean isAuthenticated(Authentication auth) {
        return auth != null && auth.isAuthenticated();
    }


    static boolean evalExpression(String expression, SecurityExpressionSupport support,
            Map<String, Object> contextExtend, Map<String, Object> functionExtend) {
        JexlContext context = new MapContext();
        context.set("auth", support.getAuthentication());
        contextExtend.forEach(context::set);
        Map<String, Object> functionMap = new HashMap<>();
        functionMap.put(null, support);
        functionMap.putAll(functionExtend);
        JexlEngine jexl = new JexlBuilder().namespaces(functionMap).safe(false).create();
        JexlExpression expr = jexl.createExpression(expression);
        return (Boolean) expr.evaluate(context);
    }

    static boolean evalExpression(String expression, SecurityExpressionSupport support,
            Map<String, Object> contextExtend) {
        return evalExpression(expression, support, contextExtend, new HashMap<>());
    }

}
