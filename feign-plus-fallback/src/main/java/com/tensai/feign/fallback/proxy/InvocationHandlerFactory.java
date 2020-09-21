package com.tensai.feign.fallback.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;


public interface InvocationHandlerFactory {

    InvocationHandler create(Map<Method, MethodHandler> methodHandlers);

    class Default implements InvocationHandlerFactory {
        @Override
        public InvocationHandler create(Map<Method, MethodHandler> methodHandlers) {
            return new ProxyInvocationHandler(methodHandlers);
        }
    }

}
