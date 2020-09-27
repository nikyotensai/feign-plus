package com.github.nikyotensai.feign;

import java.util.concurrent.atomic.AtomicInteger;

public class DefaultContextIdCreator implements ContextIdCreator {

    private static final AtomicInteger counter = new AtomicInteger();

    @Override
    public String convertName2Id(String name) {
        return name + counter.getAndIncrement();
    }
}