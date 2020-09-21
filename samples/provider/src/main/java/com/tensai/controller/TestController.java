package com.tensai.controller;

import com.tensai.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/getSth", method = RequestMethod.GET)
    public Result<Object> getSth() {
        return Result.create("0", "success");
    }

    @RequestMapping(value = "/postSth", method = RequestMethod.POST)
    public Result<Object> postSth() {
        return Result.create("0", "success");
    }

}
