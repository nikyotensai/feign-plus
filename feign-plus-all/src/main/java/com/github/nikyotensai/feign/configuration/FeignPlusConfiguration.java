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
package com.github.nikyotensai.feign.configuration;

import com.github.nikyotensai.feign.fallback.DynamicFallbackFactory;
import com.github.nikyotensai.feign.fallback.proxy.ProxyBuilder;
import feign.hystrix.FallbackFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.DynamicTargeter;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConditionalOnClass(FeignClient.class)
@AutoConfigureAfter(FeignAutoConfiguration.class)
@ConditionalOnProperty(value = {"feignPlus.fallback.enabled"}, matchIfMissing = true)
public class FeignPlusConfiguration {

    @Bean
    public FallbackFactory fallbackFactory(ProxyBuilder feignFallbackProxyBuilder) {
        return new DynamicFallbackFactory(feignFallbackProxyBuilder);
    }


    @Bean
    @ConditionalOnMissingBean
    @Primary
    public DynamicTargeter dynamicTargeter() {
        return new DynamicTargeter();
    }

    @Bean
    @ConditionalOnMissingBean
    public ProxyBuilder feignFallbackProxyBuilder() {
        return new ProxyBuilder();
    }

}
