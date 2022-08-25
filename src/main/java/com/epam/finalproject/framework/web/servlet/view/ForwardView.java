package com.epam.finalproject.framework.web.servlet.view;

import com.epam.finalproject.framework.web.servlet.View;

public class ForwardView implements View {
    String targetPath;

    public ForwardView(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getTargetPath() {
        return targetPath;
    }
}
