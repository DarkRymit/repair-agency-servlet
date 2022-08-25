package com.epam.finalproject.framework.security.support;

public class SecurityContextHolder {
    private static final ThreadLocal<SecurityContext> securityContextThreadLocal = new ThreadLocal<>();

    private SecurityContextHolder() {
    }

    public static void resetContext() {
        securityContextThreadLocal.remove();
    }

    public static void setContext(SecurityContext context) {
        if (context == null) {
            resetContext();
        } else {
            securityContextThreadLocal.set(context);
        }
    }

    public static SecurityContext getContext() {
        return securityContextThreadLocal.get();
    }

    public static SecurityContext createEmptyContext() {
        return new SecurityContext();
    }

}
