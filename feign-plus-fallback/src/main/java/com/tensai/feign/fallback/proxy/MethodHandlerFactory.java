package com.tensai.feign.fallback.proxy;

import feign.Contract;
import feign.Feign;
import feign.MethodMetadata;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public interface MethodHandlerFactory {

    MethodHandler create(Method method, ProxyMethodMetadata metadata);

    class Default implements MethodHandlerFactory {

        private final Contract contract;
        private final Map<Class, Map<String, MethodMetadata>> feignMethodMetadatas = new ConcurrentHashMap<>();

        public Default() {
            this(new SpringMvcContract());
        }

        public Default(Contract contract) {
            this.contract = contract;
        }

        @Override
        public MethodHandler create(Method method, ProxyMethodMetadata proxyMethodMetadata) {
            return new CommonMethodHandler(method, proxyMethodMetadata, getMethodMetadata(method));
        }

        private MethodMetadata getMethodMetadata(Method method) {
            Class declaringClass = method.getDeclaringClass();
            Map<String, MethodMetadata> methodMetadatas = getFeignMethodMetadatas(declaringClass);
            return methodMetadatas.get(Feign.configKey(declaringClass, method));
        }

        private Map<String, MethodMetadata> getFeignMethodMetadatas(Class clazz) {
            return feignMethodMetadatas.computeIfAbsent(clazz, (k) -> parseFeignMethodMetadatas(clazz));
        }

        private Map<String, MethodMetadata> parseFeignMethodMetadatas(Class clazz) {
            return contract.parseAndValidatateMetadata(clazz).stream()
                    .collect(Collectors.toMap(MethodMetadata::configKey, m -> m));
        }


    }

}
