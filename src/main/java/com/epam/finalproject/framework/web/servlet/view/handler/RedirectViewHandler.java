package com.epam.finalproject.framework.web.servlet.view.handler;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.ui.RedirectAttributes;
import com.epam.finalproject.framework.web.FlashAttributesManager;
import com.epam.finalproject.framework.web.WebHttpPair;
import com.epam.finalproject.framework.web.servlet.ViewHandler;
import com.epam.finalproject.framework.web.servlet.view.RedirectView;
import com.epam.finalproject.framework.web.support.RedirectAttributesContext;

import java.io.IOException;

@Component
public class RedirectViewHandler implements ViewHandler<RedirectView> {

    FlashAttributesManager flashAttributesManager;

    public RedirectViewHandler(FlashAttributesManager flashAttributesManager) {
        this.flashAttributesManager = flashAttributesManager;
    }

    @Override
    public void handle(RedirectView view, WebHttpPair pair) {
        RedirectAttributes redirectAttributes = RedirectAttributesContext.getRedirectAttributes();
        if (redirectAttributes != null) {
            flashAttributesManager.updateFlashMap(redirectAttributes.getFlashAttributes(), pair.getRequest());
        }
        try {
            String target = view.getTargetPath();
            if (target.startsWith("/")){
                target = pair.getRequest().getContextPath() + target;
            }
            pair.getResponse().sendRedirect(target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
