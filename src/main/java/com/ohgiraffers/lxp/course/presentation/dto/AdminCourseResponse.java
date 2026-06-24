package com.ohgiraffers.lxp.course.presentation.dto;

import com.ohgiraffers.lxp.course.application.dto.AdminCourseResult;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.entity.HiddenBy;

public record AdminCourseResponse(
        Long id,
        String title,
        String thumbnailUrl,
        String instructorName,
        long enrollmentCount,
        CourseStatus status,
        HiddenBy hiddenBy
) {

    public static AdminCourseResponse from(AdminCourseResult result) {
        return new AdminCourseResponse(
                result.id(),
                result.title(),
                result.thumbnailUrl(),
                result.instructorName(),
                result.enrollmentCount(),
                result.status(),
                result.hiddenBy()
        );
    }
}