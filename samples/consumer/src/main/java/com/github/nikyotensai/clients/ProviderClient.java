package com.github.nikyotensai.clients;


import com.github.nikyotensai.clients.fallback.CommonReturn;
import com.github.nikyotensai.entity.Result;
import com.github.nikyotensai.feign.fallback.DynamicFallbackFactory;
import com.github.nikyotensai.feign.fallback.annotation.ExceptionHandle;
import com.github.nikyotensai.feign.fallback.configuration.FeignPlusFallbackConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "provider", contextId = "666", configuration = FeignPlusFallbackConfiguration.class, fallbackFactory = DynamicFallbackFactory.class)
@CommonReturn
@ExceptionHandle
public interface ProviderClient {

    @RequestMapping(value = "/test/getSth", method = RequestMethod.GET)
    Result<Object> getSth();

    @RequestMapping(value = "/test/postSth", method = RequestMethod.POST)
    Result<Object> postSth();

}
