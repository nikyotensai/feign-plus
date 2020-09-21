package com.tensai.controller;

import com.tensai.clients.ProviderClient;
import com.tensai.entity.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ProviderClient providerClient;

    @RequestMapping(value = "/getSth", method = RequestMethod.GET)
    public Result<Object> getSth() {
        return providerClient.getSth();
    }

    @RequestMapping(value = "/postSth", method = RequestMethod.POST)
    public Result<Object> postSth() {
        return providerClient.postSth();
    }

}
