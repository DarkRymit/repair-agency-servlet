package com.epam.finalproject.framework.web.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestObject {
    Class<?> value() default DEFAULT.class;
    
    final class DEFAULT {}
}
