package com.epam.finalproject.framework.beans.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@Documented
public @interface Configuration {
    String value() default "";
}
