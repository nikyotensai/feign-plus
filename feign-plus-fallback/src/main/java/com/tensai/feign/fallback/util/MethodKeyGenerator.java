package com.tensai.feign.fallback.util;


import feign.Feign;

import java.lang.reflect.Method;


public class MethodKeyGenerator {

    public static String generateKey(Method method) {
        return Feign.configKey(method.getDeclaringClass(), method);
    }

}
