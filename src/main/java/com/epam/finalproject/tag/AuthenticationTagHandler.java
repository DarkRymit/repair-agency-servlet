package com.epam.finalproject.tag;

import com.epam.finalproject.framework.security.Authentication;
import com.epam.finalproject.framework.security.support.SecurityContextHolder;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.io.Writer;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class AuthenticationTagHandler extends TagSupport {

    private String expr;

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

    public void setExpr(String expr) {
        this.expr = expr;
    }

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

