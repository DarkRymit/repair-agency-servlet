package com.epam.finalproject.model.event;


import com.epam.finalproject.framework.context.ApplicationEvent;
import com.epam.finalproject.model.entity.User;

import java.util.Locale;

public class OnPasswordResetEvent extends ApplicationEvent {

    private final Locale locale;
    private final User user;

    public OnPasswordResetEvent(final User user, final Locale locale) {
        super(user);
        this.user = user;
        this.locale = locale;
    }


    public Locale getLocale() {
        return this.locale;
    }

    public User getUser() {
        return this.user;
    }
}
