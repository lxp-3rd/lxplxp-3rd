package com.ohgiraffers.lxp.content.presentation.dto;

import com.ohgiraffers.lxp.content.infrastructure.persistence.jpa.ContentJpaEntity;

public record ContentResponse(
        Long id,
        Long courseId,
        int order,
        String title,
        String contentUrl
) {

    public static ContentResponse from(ContentJpaEntity entity) {
        return new ContentResponse(
                entity.getId(),
                entity.getCourseId(),
                entity.getSequence(),
                entity.getTitle(),
                entity.getContentUrl()
        );
    }
}
