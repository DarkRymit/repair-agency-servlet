package com.epam.finalproject.framework.web.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(
        method = {RequestMethod.GET}
)
public @interface PostMapping {

    String[] value() default {};

}
