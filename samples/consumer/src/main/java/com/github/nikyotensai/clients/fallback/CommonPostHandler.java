package com.github.nikyotensai.clients.fallback;

import com.github.nikyotensai.entity.Result;
import com.github.nikyotensai.feign.fallback.handle.HandleContext;
import com.github.nikyotensai.feign.fallback.handle.PostHandler;

public class CommonPostHandler implements PostHandler {

    @Override
    public Object apply(HandleContext handleContext) {
        return Result.create("1", "fallback", null);
    }

}
