package com.tensai.feign.fallback.proxy;


import com.tensai.feign.fallback.util.MethodKeyGenerator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ProxyBuilder {

    private final InvocationHandlerFactory invocationHandlerFactory;
    private final MethodHandlerFactory methodHandlerFactory;
    private final ProxyContract proxyContract;


    public ProxyBuilder() {
        this(new InvocationHandlerFactory.Default(),
                new MethodHandlerFactory.Default(),
                new ProxyContract.Default());
    }


    public ProxyBuilder(InvocationHandlerFactory invocationHandlerFactory,
                        MethodHandlerFactory methodHandlerFactory,
                        ProxyContract proxyContract) {
        this.invocationHandlerFactory = invocationHandlerFactory;
        this.methodHandlerFactory = methodHandlerFactory;
        this.proxyContract = proxyContract;
    }

    public <T> T newInstance(Class<T> clazz) {
        Map<String, ProxyMethodMetadata> metadataMap = proxyContract.parseProxyMetadata(clazz);
        Map<Method, MethodHandler> methodHandlers = new HashMap<>(metadataMap.size());
        List<DefaultMethodHandler> defaultMethodHandlers = new ArrayList<>();

        for (Method method : clazz.getMethods()) {
            if (method.isDefault()) {
                defaultMethodHandlers.add(new DefaultMethodHandler(method));
                continue;
            }
            ProxyMethodMetadata proxyMethodMetadata = metadataMap.get(MethodKeyGenerator.generateKey(method));
            methodHandlers.put(method, methodHandlerFactory.create(method, proxyMethodMetadata));
        }

        InvocationHandler invocationHandler = invocationHandlerFactory.create(methodHandlers);
        T bean = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, invocationHandler);
        for (DefaultMethodHandler defaultMethodHandler : defaultMethodHandlers) {
            defaultMethodHandler.bindTo(bean);
        }
        return bean;
    }


}
