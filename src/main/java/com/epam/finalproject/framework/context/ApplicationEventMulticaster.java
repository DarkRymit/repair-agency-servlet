package com.epam.finalproject.framework.context;


public interface ApplicationEventMulticaster {

    void addApplicationListenerBean(String listenerBeanName);

    void removeApplicationListenerBean(String listenerBeanName);

    void removeAllListeners();

    void multicastEvent(ApplicationEvent event);

}