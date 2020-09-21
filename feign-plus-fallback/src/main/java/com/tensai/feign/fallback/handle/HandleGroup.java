package com.tensai.feign.fallback.handle;

import com.tensai.feign.fallback.annotation.Handle;
import com.tensai.feign.fallback.annotation.Return;
import com.tensai.feign.fallback.proxy.HandleMetadata;
import lombok.Data;

import java.util.Collection;


@Data
public class HandleGroup {

    private Collection<HandleMetadata<Handle>> handles;
    private Return returnHandle;

}
