package com.epam.finalproject.framework.context;

import java.util.EventListener;

/**
 * The interface Application listener.
 *
 * @param <T> the type parameter of {@link ApplicationEvent} event
 * @see ApplicationEventMulticaster ApplicationEventMulticaster
 */
public interface ApplicationListener<T extends ApplicationEvent> extends EventListener {
    /**
     * On application event.
     * <p>
     * Performs logic of handling event object
     * </p>
     *
     * @param event the event object
     */
    void onApplicationEvent(T event);

    /**
     * Listen class.
     * <p>
     *     This method affects whether {@link ApplicationListener#onApplicationEvent(ApplicationEvent)} is performed or not.
     * </p>
     * @return the class of event to listen by listener.
     */
    Class<T> listenClass();
}