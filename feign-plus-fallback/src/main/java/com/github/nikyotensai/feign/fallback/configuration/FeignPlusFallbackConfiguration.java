package com.github.nikyotensai.feign.fallback.configuration;

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
public class FeignPlusFallbackConfiguration {

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
