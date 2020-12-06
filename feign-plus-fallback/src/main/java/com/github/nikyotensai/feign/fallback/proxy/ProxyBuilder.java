/**
 * Copyright (c) 2020-2050 NikyoTensai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.nikyotensai.feign.fallback.proxy;


import com.github.nikyotensai.feign.fallback.util.MethodKeyGenerator;

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
