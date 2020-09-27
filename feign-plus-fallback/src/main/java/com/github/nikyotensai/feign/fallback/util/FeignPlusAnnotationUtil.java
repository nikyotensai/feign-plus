package com.github.nikyotensai.feign.fallback.util;

import lombok.Data;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;

@SuppressWarnings("unchecked")
public class FeignPlusAnnotationUtil {


    public static <A extends Annotation> List<A> getTaggingAnnotations(AnnotatedElement element,
                                                                       Class<A> taggingAnnoCls,
                                                                       boolean searchChild,
                                                                       boolean repeatable) {

        Annotation[] annotations = AnnotationUtils.getAnnotations(element);
        if (Objects.isNull(annotations)) {
            return Collections.emptyList();
        }
        List<A> annotationTaggingMetadatas = new ArrayList<>(annotations.length);
        for (Annotation annotation : annotations) {
            if (Objects.equals(annotation.annotationType(), taggingAnnoCls)) {
                annotationTaggingMetadatas.add((A) annotation);
            } else if (repeatable) {
                annotationTaggingMetadatas.addAll(getRepeatableTaggingAnnotationMetadatas(annotation, taggingAnnoCls));
            } else if (searchChild) {
                A taggingAnno = AnnotatedElementUtils.getMergedAnnotation(annotation.annotationType(), taggingAnnoCls);
                if (Objects.isNull(taggingAnno)) {
                    continue;
                }
                annotationTaggingMetadatas.add(taggingAnno);
            }
        }
        return annotationTaggingMetadatas;
    }


    public static <A extends Annotation> Set<A> getRepeatableTaggingAnnotationMetadatas(
            Annotation annotation, Class<A> taggingAnnoCls) {
        return AnnotatedElementUtils.getMergedRepeatableAnnotations(annotation.annotationType(), taggingAnnoCls);
    }

    @Data
    public static class TaggingAnnotationMetadata<A extends Annotation> {
        private A taggingAnnotation;
    }
}
