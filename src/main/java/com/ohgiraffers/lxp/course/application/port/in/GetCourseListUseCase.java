package com.ohgiraffers.lxp.course.application.port.in;

import com.ohgiraffers.lxp.course.application.dto.CourseResult;

import java.util.List;

public interface GetCourseListUseCase {

    List<CourseResult> getCourseList();
}
