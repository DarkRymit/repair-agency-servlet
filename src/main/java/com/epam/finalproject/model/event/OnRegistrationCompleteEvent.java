package com.epam.finalproject.model.event;


import com.epam.finalproject.framework.context.ApplicationEvent;
import com.epam.finalproject.model.entity.User;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final User user;

    public OnRegistrationCompleteEvent(final User user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return this.appUrl;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public User getUser() {
        return this.user;
    }
}
