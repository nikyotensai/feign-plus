package com.tensai.feign.fallback.annotation;


import com.tensai.feign.fallback.handle.PreHandler;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Repeatable(Handles.class)
public @interface Handle {

    Class<? extends PreHandler> handler();

    int order() default 0;
}
