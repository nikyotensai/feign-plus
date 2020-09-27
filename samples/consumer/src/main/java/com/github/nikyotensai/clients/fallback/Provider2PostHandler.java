package com.github.nikyotensai.clients.fallback;

import com.github.nikyotensai.feign.fallback.handle.HandleContext;
import com.github.nikyotensai.feign.fallback.handle.PostHandler;

public class Provider2PostHandler implements PostHandler {
    @Override
    public Object apply(HandleContext handleContext) {
        return null;
    }
}
