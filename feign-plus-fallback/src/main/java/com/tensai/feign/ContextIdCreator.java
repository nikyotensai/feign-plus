package com.tensai.feign;

/**
 * Create contextId by name/value.
 */
public interface ContextIdCreator {

    String convertName2Id(String name);

}
