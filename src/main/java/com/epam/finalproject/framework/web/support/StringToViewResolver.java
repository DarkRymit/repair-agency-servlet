package com.epam.finalproject.framework.web.support;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.web.servlet.View;
import com.epam.finalproject.framework.web.servlet.view.ForwardView;
import com.epam.finalproject.framework.web.servlet.view.JSPView;
import com.epam.finalproject.framework.web.servlet.view.RedirectView;

@Component
public class StringToViewResolver {

    public static final String REDIRECT = "redirect:";
    public static final String FORWARD = "forward:";

    public View resolve(String target) {
        if (target.startsWith(REDIRECT)) {
            return new RedirectView(target.substring(REDIRECT.length()));
        } else if (target.startsWith(FORWARD)) {
            return new ForwardView(target.substring(FORWARD.length()));
        } else {
            return new JSPView(target);
        }
    }

}
