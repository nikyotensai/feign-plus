package com.github.nikyotensai.controller;

import com.github.nikyotensai.clients.ProviderPlusClient;
import com.github.nikyotensai.entity.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ProviderPlusClient providerPlusClient;

    @RequestMapping(value = "/getSth", method = RequestMethod.GET)
    public Result<Object> getSth() {
        return providerPlusClient.getSth();
    }

    @RequestMapping(value = "/postSth", method = RequestMethod.POST)
    public Result<Object> postSth() {
        return providerPlusClient.postSth();
    }

}
