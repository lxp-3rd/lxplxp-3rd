package com.ohgiraffers.lxp.course.application.dto;

import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.entity.HiddenBy;

public record CourseResult(
        Long id,
        Long instructorId,
        String title,
        String description,
        String thumbnailUrl,
        CourseStatus status,
        HiddenBy hiddenBy,
        long likeCount
) {}
