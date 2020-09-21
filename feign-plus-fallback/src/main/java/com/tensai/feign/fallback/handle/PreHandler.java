package com.tensai.feign.fallback.handle;


public interface PreHandler extends Handler {

    void handle(HandleContext handleContext);

}
