package com.ohgiraffers.lxp.course.presentation.dto;

import java.util.List;

import com.ohgiraffers.lxp.course.domain.model.read.CourseDetail;
import com.ohgiraffers.lxp.course.domain.model.read.CurriculumItem;

public record CourseDetailResponse(
        Long id,
        String title,
        String summary,
        String description,
        String thumbnailUrl,
        long enrollmentCount,
        boolean enrolled,
        List<CurriculumItemResponse> curriculum
) {
    public static CourseDetailResponse from(CourseDetail detail) {
        return new CourseDetailResponse(
                detail.id(),
                detail.title(),
                detail.summary(),
                detail.description(),
                detail.thumbnailUrl(),
                detail.enrollmentCount(),
                detail.enrolled(),
                detail.curriculum().stream().map(CurriculumItemResponse::from).toList()
        );
    }

    public record CurriculumItemResponse(Long id, int order, String title) {
        public static CurriculumItemResponse from(CurriculumItem item) {
            return new CurriculumItemResponse(item.id(), item.order(), item.title());
        }
    }
}
