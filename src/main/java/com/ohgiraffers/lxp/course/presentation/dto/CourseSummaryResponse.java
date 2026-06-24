package com.ohgiraffers.lxp.course.presentation.dto;

import com.ohgiraffers.lxp.course.domain.model.read.CourseSummary;

public record CourseSummaryResponse(
        Long id,
        String title,
        String thumbnailUrl,
        String instructorName,
        long enrollmentCount,
        long likeCount
) {
    public static CourseSummaryResponse from(CourseSummary summary) {
        return new CourseSummaryResponse(
                summary.id(),
                summary.title(),
                summary.thumbnailUrl(),
                summary.instructorName(),
                summary.enrollmentCount(),
                summary.likeCount()
        );
    }
}
