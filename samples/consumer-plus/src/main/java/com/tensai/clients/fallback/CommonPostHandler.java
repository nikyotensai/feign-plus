package com.tensai.clients.fallback;

import com.tensai.entity.Result;
import com.tensai.feign.fallback.handle.HandleContext;
import com.tensai.feign.fallback.handle.PostHandler;

public class CommonPostHandler implements PostHandler {

    @Override
    public Object apply(HandleContext handleContext) {
        return Result.create("1", "fallback", null);
    }

}
