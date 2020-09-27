package com.github.nikyotensai.feign.fallback.proxy;

import com.github.nikyotensai.feign.fallback.annotation.Handle;
import com.github.nikyotensai.feign.fallback.annotation.Handles;
import com.github.nikyotensai.feign.fallback.annotation.Return;
import com.github.nikyotensai.feign.fallback.handle.HandleGroup;
import com.github.nikyotensai.feign.fallback.handle.PostHandler;
import com.github.nikyotensai.feign.fallback.handle.PreHandler;
import com.github.nikyotensai.feign.fallback.util.FeignPlusAnnotationUtil;
import com.github.nikyotensai.feign.fallback.util.MethodKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public interface ProxyContract {

    Map<String, ProxyMethodMetadata> parseProxyMetadata(Class<?> targetType);

    @Slf4j
    @SuppressWarnings("unchecked")
    class Default implements ProxyContract {

        private final Map<Class, Object> instances = new ConcurrentHashMap<>();

        @Override
        public Map<String, ProxyMethodMetadata> parseProxyMetadata(Class<?> targetType) {
            Map<String, ProxyMethodMetadata> result = new LinkedHashMap<>();
            for (Method method : targetType.getMethods()) {
                // don't process stattic or default method
                if (Modifier.isStatic(method.getModifiers())
                        || method.isDefault()) {
                    continue;
                }
                ProxyMethodMetadata metadata = parseMetadata(targetType, method);
                result.put(metadata.getMethodKey(), metadata);
            }
            return result;
        }

        protected ProxyMethodMetadata parseMetadata(Class<?> targetType, Method method) {
            ProxyMethodMetadata metadata = new ProxyMethodMetadata();
            metadata.setMethodKey(MethodKeyGenerator.generateKey(method));
            // method return annotation
            Return returnHandle = getReturnHandleMetadataOnAnnotatedElement(method);
            // set returnHandler
            if (Objects.nonNull(returnHandle)) {
                metadata.setPostHandler(toReturnHandlerMetadata(returnHandle));
            }
            // class annotation
            boolean ignoreReturn = Objects.nonNull(metadata.getPostHandler());
            HandleGroup handleGroup = targetType.getInterfaces().length == 1 ?
                    getHandleGroupOnClass(targetType.getInterfaces()[0], ignoreReturn) : getHandleGroupOnClass(targetType, ignoreReturn);
            if (Objects.nonNull(handleGroup.getReturnHandle())) {
                metadata.setPostHandler(toReturnHandlerMetadata(handleGroup.getReturnHandle()));
            }
            // method handle annotation
            Collection<HandleMetadata<Handle>> methodHandles = getHandleMetadatasOnAnnotatedElement(method);
            List<HandleMetadata<Handle>> handleMetadatas = new ArrayList<>(methodHandles);
            if (Objects.nonNull(handleGroup.getHandles())) {
                handleMetadatas.addAll(handleGroup.getHandles());
            }
            handleMetadatas.sort(Comparator.comparing(HandleMetadata::getOrder));
            List<PreHandler> preHandlers = handleMetadatas.stream()
                    .map(hm -> getHandlerInstance(hm.getHandleAnnotation().handler()))
                    .collect(Collectors.toList());

            metadata.setPreHandlers(preHandlers);
            return metadata;
        }


        protected HandleGroup getHandleGroupOnClass(Class<?> clazz, boolean ignoreReturnHandle) {
            HandleGroup handleGroup = new HandleGroup();
            if (!ignoreReturnHandle) {
                Return returnHandle = getReturnHandleMetadataOnAnnotatedElement(clazz);
                handleGroup.setReturnHandle(returnHandle);
            }
            handleGroup.setHandles(getHandleMetadatasOnAnnotatedElement(clazz));
            return handleGroup;
        }

        protected List<HandleMetadata<Handle>> getHandleMetadatasOnAnnotatedElement(AnnotatedElement element) {
            List<Handle> handleAnnotations = FeignPlusAnnotationUtil.getTaggingAnnotations(element, Handle.class, true, true);

            List<HandleMetadata<Handle>> handleMetadataList = handleAnnotations.stream().map(handleAnnotation -> {
                int order = handleAnnotation.order();
                return new HandleMetadata<Handle>()
                        .setHandleAnnotation(handleAnnotation)
                        .setOrder(order);
            }).collect(Collectors.toList());

            return ListUtils.union(handleMetadataList, getHandleMetadatasOnAnnotatedElementByHandles(element));
        }

        private List<HandleMetadata<Handle>> getHandleMetadatasOnAnnotatedElementByHandles(AnnotatedElement element) {
            Handles handlesAnno = element.getAnnotation(Handles.class);
            if (Objects.isNull(handlesAnno)) {
                return Collections.emptyList();
            }

            return Arrays.stream(handlesAnno.value()).map(handle -> {
                return new HandleMetadata<Handle>()
                        .setHandleAnnotation(handle)
                        .setOrder(handle.order());
            }).collect(Collectors.toList());
        }


        protected Return getReturnHandleMetadataOnAnnotatedElement(AnnotatedElement element) {
            List<Return> annotationTaggingMetadatas = FeignPlusAnnotationUtil.getTaggingAnnotations(element, Return.class, true, false);
            if (annotationTaggingMetadatas.isEmpty()) {
                return null;
            }
            return annotationTaggingMetadatas.get(0);
        }

        protected PostHandler toReturnHandlerMetadata(Return returnHandle) {
            return getHandlerInstance(returnHandle.handler());
        }

        protected <T> T getHandlerInstance(Class<T> clazz) {
            try {
                return getInstance(clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        protected <T> T getInstance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
            Object bean = instances.get(clazz);
            if (Objects.isNull(bean)) {
                bean = clazz.newInstance();
                instances.put(clazz, bean);
            }
            return (T) bean;
        }

    }

}
