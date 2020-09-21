package com.tensai.feign.fallback.proxy;

import com.tensai.feign.fallback.handle.HandleContext;
import com.tensai.feign.fallback.handle.PostHandler;
import com.tensai.feign.fallback.handle.PreHandler;
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
