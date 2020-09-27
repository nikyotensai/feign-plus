package com.github.nikyotensai.feign.fallback.handle;

public interface PostHandler extends Handler {

    Object apply(HandleContext arg);

}
