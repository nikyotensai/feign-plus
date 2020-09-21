package com.tensai.feign.fallback.proxy;


public interface MethodHandler {

    Object invoke(Object[] args) throws Throwable;

}
