package com.tensai.feign.fallback.handle;

public interface PostHandler extends Handler {

    Object apply(HandleContext arg);

}
