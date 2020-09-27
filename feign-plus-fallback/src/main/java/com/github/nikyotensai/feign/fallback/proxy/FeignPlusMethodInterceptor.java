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
