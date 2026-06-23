package com.ohgiraffers.lxp.enrollment.application.port.out;

import com.ohgiraffers.lxp.enrollment.application.dto.CourseInfo;

public interface LoadCourseInfoPort {

    CourseInfo load(Long courseId);

}
