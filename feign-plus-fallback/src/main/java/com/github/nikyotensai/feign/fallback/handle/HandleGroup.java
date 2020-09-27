package com.github.nikyotensai.feign.fallback.handle;

import com.github.nikyotensai.feign.fallback.annotation.Handle;
import com.github.nikyotensai.feign.fallback.annotation.Return;
import com.github.nikyotensai.feign.fallback.proxy.HandleMetadata;
import lombok.Data;

import java.util.Collection;


@Data
public class HandleGroup {

    private Collection<HandleMetadata<Handle>> handles;
    private Return returnHandle;

}
