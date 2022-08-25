package com.epam.finalproject.framework.context.support;

import com.epam.finalproject.framework.context.ApplicationContext;

public class ApplicationContextHolder {
    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        ApplicationContextHolder.context = context;
    }
}
