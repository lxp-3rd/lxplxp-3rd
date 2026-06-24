package com.ohgiraffers.lxp.course.domain.model.read;

/**
 * 강좌 커리큘럼 항목(읽기 전용). Content 엔티티의 sequence를 order로 노출한다.
 */
public record CurriculumItem(
        Long id,
        int order,
        String title
) {}
