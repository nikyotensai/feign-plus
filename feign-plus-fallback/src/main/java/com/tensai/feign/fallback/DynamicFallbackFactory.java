package com.tensai.feign.fallback;

import com.tensai.feign.fallback.proxy.FeignPlusMethodInterceptor;
import com.tensai.feign.fallback.proxy.ProxyBuilder;
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
