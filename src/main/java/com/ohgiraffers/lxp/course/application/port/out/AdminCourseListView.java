package com.ohgiraffers.lxp.course.application.port.out;

import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.entity.HiddenBy;

/**
 * 관리자 강좌 관리 목록용 읽기 전용 행. 공개 목록과 달리 모든 상태의 강좌를 포함하며
 * status/hiddenBy를 함께 노출한다. instructorId로 강사명을 따로 조합한다.
 */
public record AdminCourseListView(
        Long id,
        String title,
        String thumbnailUrl,
        Long instructorId,
        CourseStatus status,
        HiddenBy hiddenBy
) {}