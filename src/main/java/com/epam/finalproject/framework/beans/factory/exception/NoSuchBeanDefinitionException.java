package com.epam.finalproject.framework.beans.factory.exception;

public class NoSuchBeanDefinitionException extends BeansException {
    public NoSuchBeanDefinitionException(String message) {
        super(message);
    }

    public NoSuchBeanDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchBeanDefinitionException(Throwable cause) {
        super(cause);
    }
}
