package com.ohgiraffers.lxp.course.application.port.in;

import com.ohgiraffers.lxp.course.domain.model.read.CourseDetail;

public interface GetCourseDetailUseCase {

    /**
     * @param memberId 로그인 사용자 ID. 비로그인 시 null(enrolled=false).
     */
    CourseDetail getCourseDetail(Long courseId, Long memberId);
}
