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
