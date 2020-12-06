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
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.nikyotensai.feign.logger.configuration;

import com.github.nikyotensai.feign.logger.FeignPlusLoggger;
import feign.Logger;
import feign.Logger.Level;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConditionalOnClass({Logger.class, FeignClient.class})
@ConditionalOnProperty(value = {"feignPlus.log.enabled"}, matchIfMissing = true)
public class FeignLogConfiguration {

    @Bean
    @Primary
    public Level feignLoggerLevel() {
        return Level.FULL;
    }

    @Bean
    @Primary
    public FeignLoggerFactory feignLoggerFactory() {
        return type -> {
            return new FeignPlusLoggger(type);
        };
    }

}
