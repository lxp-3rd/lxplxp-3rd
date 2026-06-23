package com.ohgiraffers.lxp.course.presentation.dto;

import com.ohgiraffers.lxp.course.application.dto.CourseResult;

public record CourseResponse(
        Long id,
        Long instructorId,
        String title,
        String description,
        String thumbnailUrl,
        String status,
        String hiddenBy,
        long likeCount
) {
    public static CourseResponse from(CourseResult result) {
        return new CourseResponse(
                result.id(),
                result.instructorId(),
                result.title(),
                result.description(),
                result.thumbnailUrl(),
                result.status().name(),
                result.hiddenBy() != null ? result.hiddenBy().name() : null,
                result.likeCount()
        );
    }
}
