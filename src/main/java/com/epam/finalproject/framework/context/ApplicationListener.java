package com.epam.finalproject.framework.context;

import java.util.EventListener;

public interface ApplicationListener<T extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(T event);
    Class<T> listenClass();
}