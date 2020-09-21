//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tensai.feign.logger.configuration;

import com.tensai.feign.logger.FeignPlusLoggger;
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
