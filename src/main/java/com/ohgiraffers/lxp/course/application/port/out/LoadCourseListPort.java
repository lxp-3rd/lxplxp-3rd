package com.ohgiraffers.lxp.course.application.port.out;

import java.util.List;

public interface LoadCourseListPort {

    /**
     * status='PUBLIC' AND deleted_at IS NULL 강좌를 created_at DESC로 조회한다.
     */
    List<CourseListView> loadPublicCourses();

    /**
     * deleted_at IS NULL 인 모든 강좌(상태 무관)를 created_at DESC로 조회한다. 관리자 강좌 관리 화면 전용.
     */
    List<AdminCourseListView> loadAllCourses();
}
