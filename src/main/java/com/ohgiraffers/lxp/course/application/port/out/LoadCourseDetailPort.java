package com.ohgiraffers.lxp.course.application.port.out;

import java.util.Optional;

public interface LoadCourseDetailPort {

    /**
     * status='PUBLIC' AND deleted_at IS NULL 강좌의 상세 + 커리큘럼(sequence ASC)을 조회한다.
     * 조건을 만족하는 강좌가 없으면 빈 Optional.
     */
    Optional<CourseDetailView> loadPublicCourseDetail(Long courseId);
}
