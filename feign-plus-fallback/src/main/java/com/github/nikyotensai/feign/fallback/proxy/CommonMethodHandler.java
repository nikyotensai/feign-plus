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

import com.github.nikyotensai.feign.fallback.handle.HandleContext;
import com.github.nikyotensai.feign.fallback.handle.PostHandler;
import com.github.nikyotensai.feign.fallback.handle.PreHandler;
import feign.MethodMetadata;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Objects;


@Getter
public class CommonMethodHandler implements MethodHandler {

    private Method method;
    private ProxyMethodMetadata proxyMethodMetadata;
    private MethodMetadata methodMetadata;

    public CommonMethodHandler(Method method, ProxyMethodMetadata proxyMethodMetadata, MethodMetadata methodMetadata) {
        this.method = method;
        this.proxyMethodMetadata = proxyMethodMetadata;
        this.methodMetadata = methodMetadata;
    }

    @Override
    public Object invoke(Object[] args) throws Throwable {
        HandleContext handleContext = HandleContextHolder.getLocalHandleContext();
        if (handleContext == null) {
            handleContext = new HandleContext();
        }
        fillContext(handleContext, args);
        return handle(handleContext);
    }


    private void fillContext(HandleContext handleContext, Object[] args) {
        handleContext.setArgs(args);
        handleContext.setMethod(method);
        handleContext.setMethodMetadata(methodMetadata);
    }


    protected Object handle(HandleContext handleContext) {
        for (PreHandler preHandler : proxyMethodMetadata.getPreHandlers()) {
            preHandler.handle(handleContext);
        }
        PostHandler postHandler = proxyMethodMetadata.getPostHandler();
        return Objects.isNull(postHandler) ? null : postHandler.apply(handleContext);
    }

}
