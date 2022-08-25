package com.epam.finalproject.framework.beans.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
    String value() default "";
    Class<?> clazz() default DEFAULT.class;

    final class DEFAULT {}
}
