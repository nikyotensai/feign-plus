package com.github.nikyotensai.feign.fallback.annotation;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Handles {

    Handle[] value() default {};
}
