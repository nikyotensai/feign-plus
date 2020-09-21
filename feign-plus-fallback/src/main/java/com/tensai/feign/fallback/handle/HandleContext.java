package com.tensai.feign.fallback.handle;

import feign.MethodMetadata;
import lombok.Data;

import java.lang.reflect.Method;


@Data
public class HandleContext {

    private Throwable cause;

    private Method method;

    private Object[] args;

    private MethodMetadata methodMetadata;

}
