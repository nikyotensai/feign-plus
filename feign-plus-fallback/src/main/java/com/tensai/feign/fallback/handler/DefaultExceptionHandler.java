package com.tensai.feign.fallback.handler;

import com.tensai.feign.fallback.handle.HandleContext;
import com.tensai.feign.fallback.handle.PreHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;


@Slf4j
public class DefaultExceptionHandler implements PreHandler {

    @Override
    public void handle(HandleContext handleContext) {
        log.error("Fallback! configKey:{},requestBody:{}, cause: ",
                handleContext.getMethodMetadata().configKey(),
                Arrays.toString(handleContext.getArgs()),
                handleContext.getCause());
    }

}
