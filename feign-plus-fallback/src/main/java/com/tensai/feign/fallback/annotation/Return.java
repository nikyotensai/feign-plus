package com.tensai.feign.fallback.annotation;

import com.tensai.feign.fallback.handle.PostHandler;

import java.lang.annotation.*;


@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Return {

    Class<? extends PostHandler> handler();

}
