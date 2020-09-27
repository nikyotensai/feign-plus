package com.github.nikyotensai.feign.fallback.handle;


public interface PreHandler extends Handler {

    void handle(HandleContext handleContext);

}
