package com.epam.finalproject.tag;

import com.epam.finalproject.framework.security.Authentication;
import com.epam.finalproject.framework.security.support.SecurityContextHolder;
import com.epam.finalproject.framework.security.support.SecurityExpressionSupport;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.jstl.core.ConditionalTagSupport;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.epam.finalproject.framework.security.util.SecurityUtils.*;

public class AuthorizeTagHandler extends ConditionalTagSupport {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthorizeTagHandler.class);
    private String expr;

    public AuthorizeTagHandler() {
        this.init();
    }

    @Override
    protected boolean condition() throws JspTagException {
        log.trace("Security Context jsp : {}",SecurityContextHolder.getContext());
        SecurityExpressionSupport support = new SecurityExpressionSupport( SecurityContextHolder.getContext());
        Map<String,Object> contextMap = new HashMap<>();
        contextMap.put("req",pageContext.getRequest());
        contextMap.put("res",pageContext.getResponse());
        return evalExpression(expr, support,contextMap);

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
