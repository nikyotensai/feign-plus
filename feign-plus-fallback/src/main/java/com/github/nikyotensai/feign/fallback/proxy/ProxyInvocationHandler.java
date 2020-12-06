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
