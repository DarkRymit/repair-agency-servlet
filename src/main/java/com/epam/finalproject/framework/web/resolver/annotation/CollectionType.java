package com.epam.finalproject.framework.web.resolver.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CollectionType {
    Class<?> value();
}
