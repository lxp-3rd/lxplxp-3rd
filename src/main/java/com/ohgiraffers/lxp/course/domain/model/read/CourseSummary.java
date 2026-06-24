package com.ohgiraffers.lxp.course.domain.model.read;

/**
 * 강좌 목록(카드 그리드)용 읽기 전용 모델.
 * Course 애그리거트를 거치지 않는 CQRS-lite 조회 경로에서 사용한다.
 */
public record CourseSummary(
        Long id,
        String title,
        String thumbnailUrl,
        String instructorName,
        long enrollmentCount,
        long likeCount
) {}
