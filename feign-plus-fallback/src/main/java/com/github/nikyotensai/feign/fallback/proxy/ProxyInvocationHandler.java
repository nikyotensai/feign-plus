package com.github.nikyotensai.feign.fallback.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;


public class ProxyInvocationHandler implements InvocationHandler {


    private final Map<Method, MethodHandler> methodHandlers;

    public ProxyInvocationHandler(Map<Method, MethodHandler> methodHandlers) {
        this.methodHandlers = methodHandlers;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if ("toString".equals(methodName)) {
            return toString();
        }
        if ("hashCode".equals(methodName)) {
            return hashCode();
        }
        if ("equals".equals(methodName)) {
            throw new IllegalAccessException("proxy doesn't support equals method");
        }
        MethodHandler methodHandler = methodHandlers.get(method);
        if (Objects.isNull(methodHandler)) {
            return null;
        }
        return methodHandler.invoke(args);
    }
}
