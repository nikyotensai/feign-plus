package com.tensai.feign.fallback.proxy;

import lombok.Data;

import java.lang.annotation.Annotation;


@Data
public class HandleMetadata<A extends Annotation> {
    private int order;
    private A handleAnnotation;
}
