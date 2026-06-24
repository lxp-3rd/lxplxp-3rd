package com.ohgiraffers.lxp.course.application.port.out;

import java.util.List;

public interface LoadCourseListPort {

    /**
     * status='PUBLIC' AND deleted_at IS NULL 강좌를 created_at DESC로 조회한다.
     */
    List<CourseListView> loadPublicCourses();
}
