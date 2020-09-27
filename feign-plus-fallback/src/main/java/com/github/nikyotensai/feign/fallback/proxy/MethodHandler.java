package com.github.nikyotensai.feign.fallback.proxy;


public interface MethodHandler {

    Object invoke(Object[] args) throws Throwable;

}
