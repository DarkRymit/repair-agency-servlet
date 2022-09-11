package com.epam.finalproject;


import com.epam.finalproject.framework.beans.factory.config.BeanPostProcessor;
import com.epam.finalproject.framework.beans.factory.config.BeanScope;
import com.epam.finalproject.framework.beans.factory.exception.BeansException;
import com.epam.finalproject.framework.beans.factory.support.DefaultBeanDefinition;
import com.epam.finalproject.framework.beans.factory.util.BeanUtils;
import com.epam.finalproject.framework.context.ApplicationContext;
import com.epam.finalproject.framework.context.ManualConfigurableApplicationContext;
import com.epam.finalproject.framework.scanner.ClassPathScanner;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;

import java.util.Set;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ManualConfigurableApplicationContext context = new ManualConfigurableApplicationContext();
        context.registerBeanPostProcessor(beanPostProcessor())
                .registerBean(DefaultBeanDefinition.builder()
                        .beanName("servletContext")
                        .scope(BeanScope.SINGLETON.name())
                        .beanClass(ServletContext.class)
                        .supplier(() -> servletContext)
                        .build());
        ClassPathScanner scanner = new ClassPathScanner();
        Set<Class<?>> classSet = scanner.scan("com.epam.finalproject", BeanUtils::isPossiblyBean);
        log.trace("Found classes {}",classSet);
        classSet.forEach(context::registerBean);
        context.setup();
        servletContext.setAttribute("applicationContext", context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ApplicationContext context = (ApplicationContext) servletContext.getAttribute("applicationContext");
        ValidatorFactory factory = context.getBean(ValidatorFactory.class);
        factory.close();
    }

    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                log.debug("before {}", beanName);
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                log.debug("after {}", beanName);
                return bean;
            }
        };
    }
}