package com.epam.finalproject.framework.context;


/**
 * The interface Application event multicaster.
 *
 * @see com.epam.finalproject.framework.context.support.SimpleEventMulticaster SimpleEventMulticaster
 */
public interface ApplicationEventMulticaster {

    /**
     * Add application listener bean.
     *
     * @param listenerBeanName the listener bean name
     */
    void addApplicationListenerBean(String listenerBeanName);

    /**
     * Remove application listener bean.
     *
     * @param listenerBeanName the listener bean name
     */
    void removeApplicationListenerBean(String listenerBeanName);

    /**
     * Remove all listeners.
     */
    void removeAllListeners();

    /**
     * Multicast event.
     *
     * @param event the event
     */
    void multicastEvent(ApplicationEvent event);

}