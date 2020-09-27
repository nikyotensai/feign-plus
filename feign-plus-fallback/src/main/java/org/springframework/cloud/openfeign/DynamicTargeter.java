package org.springframework.cloud.openfeign;

import com.github.nikyotensai.feign.fallback.DynamicFallbackFactory;
import feign.Feign;
import feign.Target;
import feign.hystrix.FallbackFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public class DynamicTargeter implements Targeter {

    @Autowired
    private Targeter delegate;

    @Override
    public <T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignContext context, Target.HardCodedTarget<T> target) {
        FallbackFactory fallbackFactory = getFallbackFactory(context, factory);
        processDynamicFallback(fallbackFactory, factory.getType());
        return delegate.target(factory, feign, context, target);
    }

    private <T> T getFallbackFactory(FeignContext context, FeignClientFactoryBean factory) {
        String name = StringUtils.isEmpty(factory.getContextId()) ? factory.getName() : factory.getContextId();
        return context.getInstance(name, (Class<T>) factory.getFallbackFactory());
    }

    private <T> void processDynamicFallback(FallbackFactory<T> fallbackFactory, Class<T> feignClientType) {
        if (!(fallbackFactory instanceof DynamicFallbackFactory)) {
            return;
        }
        DynamicFallbackFactory<T> dynamicFallbackFactory = (DynamicFallbackFactory<T>) fallbackFactory;
        dynamicFallbackFactory.setFeignClientType(feignClientType);
    }

}
