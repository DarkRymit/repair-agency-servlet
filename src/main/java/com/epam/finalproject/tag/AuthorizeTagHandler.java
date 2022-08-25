package com.epam.finalproject.tag;

import com.epam.finalproject.framework.security.Authentication;
import com.epam.finalproject.framework.security.support.SecurityContextHolder;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.jstl.core.ConditionalTagSupport;
import org.slf4j.Logger;

public class AuthorizeTagHandler extends ConditionalTagSupport {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthorizeTagHandler.class);
    private String expr;

    public AuthorizeTagHandler() {
        this.init();
    }

    @Override
    protected boolean condition() throws JspTagException {
        log.trace("Security Context jsp : {}",SecurityContextHolder.getContext());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (expr.equals("isAuthenticated()")){
            return isAuthenticated(auth);
        }else if (expr.equals("isAnonymous()")){
            return isAnonymous(auth);
        }else if(expr.startsWith("hasRole(")&&expr.endsWith(")")){
            if(auth == null){
                return false;
            }
            return isHaveRole(auth, expr.substring("hasRole(".length(), expr.length() - 1));
        }
        return false;
    }

    private boolean isHaveRole(Authentication auth,String role) {
        return auth.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .equals("ROLE_" + role));
    }

    private boolean isAnonymous(Authentication auth) {
        return auth == null;
    }


    private boolean isAuthenticated(Authentication auth) {
        return auth != null;
    }

    @Override
    public void release() {
        super.release();
        this.init();
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    private void init() {
        this.expr = "";
    }
}
