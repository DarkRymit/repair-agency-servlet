package com.epam.finalproject.tag;

import com.epam.finalproject.framework.security.Authentication;
import com.epam.finalproject.framework.security.support.SecurityContextHolder;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.io.Writer;

/**
 * The type Authentication tag handler.
 */
public class AuthenticationTagHandler extends TagSupport {

    private String expr;

    /**
     * Instantiates a new Authentication tag handler.
     */
    public AuthenticationTagHandler() {
        init();
    }

    @Override
    public int doStartTag()  {
        try {
            Writer writer = pageContext.getOut();
            if (expr.equals("name")){
                String name = getName();
                if (name == null){
                    name = "";
                }
                writer.write(name);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SKIP_BODY;
    }

    /**
     * Sets expr.
     *
     * @param expr the expr
     */
    public void setExpr(String expr) {
        this.expr = expr;
    }

    /**
     * Get name string.
     *
     * @return the string that represents current login user username or  {@link null} otherwise
     */
    public static String getName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            return auth.getName();
        }
        return null;
    }

    @Override
    public void release() {
        super.release();
        this.init();
    }
    private void init() {
        this.expr = "";
    }
}

