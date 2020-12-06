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
package com.github.nikyotensai.feign.fallback;

import com.github.nikyotensai.feign.fallback.proxy.FeignPlusMethodInterceptor;
import com.github.nikyotensai.feign.fallback.proxy.ProxyBuilder;
import feign.hystrix.FallbackFactory;
import org.springframework.aop.framework.ProxyFactory;

@SuppressWarnings("unchecked")
public class DynamicFallbackFactory<T> implements FallbackFactory<T> {

    private final ProxyBuilder proxyBuilder;
    private Class<T> feignClientType;
    private volatile T fallback;

    public DynamicFallbackFactory(ProxyBuilder proxyBuilder) {
        this.proxyBuilder = proxyBuilder;
    }

    @Override
    public T create(Throwable cause) {
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(doCreate());
        factory.addAdvice(new FeignPlusMethodInterceptor(cause));
        return (T) factory.getProxy();
    }

    private T doCreate() {
        if (fallback != null) {
            return fallback;
        }
        synchronized (this) {
            if (fallback == null) {
                fallback = proxyBuilder.newInstance(feignClientType);
            }
        }
        return fallback;
    }

    public Class<T> getFeignClientType() {
        return feignClientType;
    }

    public void setFeignClientType(Class<T> feignClientType) {
        this.feignClientType = feignClientType;
    }
}
