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
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


public class FeignPlusMethodInterceptor implements MethodInterceptor {

    private final Throwable cause;

    public FeignPlusMethodInterceptor(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        HandleContext handleContext = new HandleContext();
        handleContext.setCause(cause);
        HandleContextHolder.setLocalHandleContext(handleContext);
        try {
            return invocation.proceed();
        } finally {
            HandleContextHolder.removeLocalHandleContext();
        }
    }
}
