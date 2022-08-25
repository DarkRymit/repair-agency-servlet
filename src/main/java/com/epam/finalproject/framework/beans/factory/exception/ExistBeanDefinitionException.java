package com.epam.finalproject.framework.beans.factory.exception;

public class ExistBeanDefinitionException extends BeansException {
    public ExistBeanDefinitionException(String message) {
        super(message);
    }

    public ExistBeanDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistBeanDefinitionException(Throwable cause) {
        super(cause);
    }
}
