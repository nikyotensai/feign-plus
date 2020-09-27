package com.github.nikyotensai.feign.fallback.annotation;

import com.github.nikyotensai.feign.fallback.handle.PostHandler;

import java.lang.annotation.*;


@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Return {

    Class<? extends PostHandler> handler();

}
