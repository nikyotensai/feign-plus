package com.github.nikyotensai.feign.fallback.proxy;

import com.github.nikyotensai.feign.fallback.handle.PostHandler;
import com.github.nikyotensai.feign.fallback.handle.PreHandler;
import lombok.Data;

import java.util.List;


@Data
public class ProxyMethodMetadata {

    private String methodKey;

    private List<PreHandler> preHandlers;

    private PostHandler postHandler;

}
