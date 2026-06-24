package com.ohgiraffers.lxp.course.application.dto;

import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.entity.HiddenBy;

/**
 * 관리자 강좌 관리 목록 항목. 강사명·수강생 수를 조합한 뒤 status/hiddenBy를 함께 노출한다.
 */
public record AdminCourseResult(
        Long id,
        String title,
        String thumbnailUrl,
        String instructorName,
        long enrollmentCount,
        CourseStatus status,
        HiddenBy hiddenBy
) {}