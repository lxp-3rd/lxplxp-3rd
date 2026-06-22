package com.ohgiraffers.lxp.enrollment.application.dto;

/**
 * LoadCourseInfoPort가 반환하는 강좌 정보(수강신청 불변식 판정에 필요한 최소 표현).
 */
public record CourseInfo(Long courseId, boolean published) {

    public boolean isPublished() {
        return published;
    }
}
