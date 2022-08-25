package com.epam.finalproject.framework.context;

import com.epam.finalproject.framework.beans.annotation.Configuration;
import com.epam.finalproject.framework.beans.factory.config.BeanDefinition;
import com.epam.finalproject.framework.beans.factory.config.BeanPostProcessor;
import com.epam.finalproject.framework.beans.factory.config.BeanScope;
import com.epam.finalproject.framework.beans.factory.support.AnnotationBeanDefinitionBuilder;
import com.epam.finalproject.framework.beans.factory.support.DefaultBeanDefinition;
import com.epam.finalproject.framework.beans.factory.support.ManualConfigurableBeanFactory;
import com.epam.finalproject.framework.context.support.AbstractApplicationContext;
import com.epam.finalproject.framework.context.support.ApplicationContextHolder;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.util.List;

public class ManualConfigurableApplicationContext extends AbstractApplicationContext {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ManualConfigurableApplicationContext.class);

    public ManualConfigurableApplicationContext() {
        factory = new ManualConfigurableBeanFactory();
    }


    public ManualConfigurableApplicationContext registerBean(BeanDefinition definition) {
        factory.registerBean(definition);
        return this;
    }

    public ManualConfigurableApplicationContext registerBean( Class<?> target) {
        BeanDefinition definition = AnnotationBeanDefinitionBuilder.build(target);
        factory.registerBean(definition);
        if (definition.getBeanClass().isAnnotationPresent(Configuration.class)) {
            for (BeanDefinition subDefinition : AnnotationBeanDefinitionBuilder.buildSubBeans(definition)) {
                factory.registerBean(subDefinition);
            }
        }
        return this;
    }

    public ManualConfigurableApplicationContext registerBeanPostProcessor(BeanPostProcessor postProcessor) {
        factory.registerBeanPostProcessor(postProcessor);
        return this;
    }


    @SneakyThrows
    public void setup() {
        ApplicationContextHolder.setContext(this);
        registerBean(DefaultBeanDefinition.builder()
                .beanName("applicationContext")
                .scope(BeanScope.SINGLETON.name())
                .beanClass(ManualConfigurableApplicationContext.class)
                .supplier(() -> this)
                .build());
        factory.constructSingletons();
        multicaster = factory.getBean(ApplicationEventMulticaster.class);
        List<BeanDefinition> listeners = factory.getBeanDefinitions(ApplicationListener.class);
        for (BeanDefinition listener : listeners) {
            multicaster.addApplicationListenerBean(listener.getBeanName());
        }
    }
}
