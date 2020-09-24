package com.tensai.clients.fallback;

import com.tensai.feign.fallback.handle.HandleContext;
import com.tensai.feign.fallback.handle.PostHandler;

public class Provider2PostHandler implements PostHandler {
    @Override
    public Object apply(HandleContext handleContext) {
        return null;
    }
}
