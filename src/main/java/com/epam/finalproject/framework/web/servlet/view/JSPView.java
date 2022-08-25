package com.epam.finalproject.framework.web.servlet.view;

import com.epam.finalproject.framework.web.servlet.View;

public class JSPView implements View {
    String targetJSP;

    public JSPView(String targetJSP) {
        this.targetJSP = targetJSP;
    }

    public String getTargetJSP() {
        return targetJSP;
    }
}
