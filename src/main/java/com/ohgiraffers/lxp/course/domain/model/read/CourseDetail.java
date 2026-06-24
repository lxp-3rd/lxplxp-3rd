package com.ohgiraffers.lxp.course.domain.model.read;

import java.util.List;

/**
 * 강좌 상세 조회용 읽기 모델. Course 애그리거트를 거치지 않는 CQRS-lite 조회 경로에서 사용한다.
 */
public record CourseDetail(
        Long id,
        String title,
        String summary,
        String description,
        String thumbnailUrl,
        long enrollmentCount,
        boolean enrolled,
        List<CurriculumItem> curriculum
) {}
