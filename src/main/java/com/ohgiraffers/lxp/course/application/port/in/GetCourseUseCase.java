package com.ohgiraffers.lxp.course.application.port.in;

import com.ohgiraffers.lxp.course.application.dto.CourseResult;

public interface GetCourseUseCase {

    CourseResult getCourse(Long courseId);
}
