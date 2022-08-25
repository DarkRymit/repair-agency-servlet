package com.epam.finalproject.framework.web.servlet.view.handler;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.web.WebHttpPair;
import com.epam.finalproject.framework.web.servlet.ViewHandler;
import com.epam.finalproject.framework.web.servlet.view.JSPView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JSPViewHandler implements ViewHandler<JSPView> {
    @Override
    public void handle(JSPView view, WebHttpPair pair) {

        String target = "/WEB-INF/pages/" + view.getTargetJSP() + ".jsp";
        try {
            HttpServletResponse response = pair.getResponse();
            response.setHeader("Cache-Control", "no-cache, no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            pair.getRequest().getRequestDispatcher(target).forward(pair.getRequest(), pair.getResponse());
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
