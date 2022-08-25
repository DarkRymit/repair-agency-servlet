package com.epam.finalproject.framework.web.servlet.view;

import com.epam.finalproject.framework.web.servlet.View;

public class RedirectView implements View {
    String targetPath;

    public RedirectView(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getTargetPath() {
        return targetPath;
    }
}
