package com.epam.finalproject.framework.web.annotation;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.beans.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Qualifier
public @interface Service {
    String value() default "";
}

