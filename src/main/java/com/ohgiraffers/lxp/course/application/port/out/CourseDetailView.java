package com.ohgiraffers.lxp.course.application.port.out;

import java.util.List;

import com.ohgiraffers.lxp.course.domain.model.read.CurriculumItem;

/**
 * Course 컨텍스트가 제공하는 강좌 상세 + 커리큘럼. 수강 정보는 별도 포트로 조합한다.
 */
public record CourseDetailView(
        Long id,
        String title,
        String description,
        String thumbnailUrl,
        List<CurriculumItem> curriculum
) {}
