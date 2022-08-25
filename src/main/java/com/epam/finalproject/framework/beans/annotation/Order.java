package com.epam.finalproject.framework.beans.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {
    int value() default Integer.MAX_VALUE;
}
