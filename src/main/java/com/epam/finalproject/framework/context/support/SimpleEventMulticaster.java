package com.epam.finalproject.framework.context.support;

import com.epam.finalproject.framework.beans.factory.BeanFactory;
import com.epam.finalproject.framework.beans.annotation.Autowire;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.context.ApplicationEvent;
import com.epam.finalproject.framework.context.ApplicationEventMulticaster;
import com.epam.finalproject.framework.context.ApplicationListener;
import com.epam.finalproject.framework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleEventMulticaster implements ApplicationEventMulticaster {

    BeanFactory beanFactory;

    Map<String,ApplicationListener<ApplicationEvent>> applicationListeners = new HashMap<>();

    @Autowire
    public SimpleEventMulticaster(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListenerBean(String listenerBeanName) {
        applicationListeners.put(listenerBeanName,beanFactory.getBean(listenerBeanName,ApplicationListener.class));
    }

    @Override
    public void removeApplicationListenerBean(String listenerBeanName) {
        applicationListeners.remove(listenerBeanName);
    }

    @Override
    public void removeAllListeners() {
        applicationListeners.clear();
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
       for(ApplicationListener<ApplicationEvent> listener : applicationListeners.values()){
           if (ClassUtils.isAssignable(event.getClass(),listener.listenClass())){
               listener.onApplicationEvent(event);
           }
       }
    }

}
