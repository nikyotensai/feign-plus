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
