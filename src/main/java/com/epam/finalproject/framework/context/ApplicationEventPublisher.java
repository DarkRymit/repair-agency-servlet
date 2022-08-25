package com.epam.finalproject.framework.context;

@FunctionalInterface
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
