package com.epam.finalproject.framework.web;

public class HandlerArgumentValidationException extends RuntimeException{


    public HandlerArgumentValidationException(String message) {
        super(message);
    }

    public HandlerArgumentValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
