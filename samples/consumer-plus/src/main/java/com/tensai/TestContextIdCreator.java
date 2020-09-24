package com.tensai;

import com.tensai.feign.ContextIdCreator;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @auther tensai
 */
public class TestContextIdCreator implements ContextIdCreator {
    @Override
    public String convertName2Id(String name) {
        return name + RandomStringUtils.randomAlphabetic(6);
    }
}
