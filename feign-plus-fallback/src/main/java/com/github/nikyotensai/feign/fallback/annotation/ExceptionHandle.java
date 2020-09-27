package com.github.nikyotensai.feign.fallback.annotation;

import com.github.nikyotensai.feign.fallback.handler.DefaultExceptionHandler;

import java.lang.annotation.*;


@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Handle(handler = DefaultExceptionHandler.class)
public @interface ExceptionHandle {
}
