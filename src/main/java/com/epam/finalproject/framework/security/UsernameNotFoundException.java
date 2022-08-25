package com.epam.finalproject.framework.security;


public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException(String msg) {
        super(msg);
    }
    public UsernameNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}