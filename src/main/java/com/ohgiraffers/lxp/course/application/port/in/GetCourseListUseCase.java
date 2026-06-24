package com.ohgiraffers.lxp.course.application.port.in;

import java.util.List;

import com.ohgiraffers.lxp.course.domain.model.read.CourseSummary;

public interface GetCourseListUseCase {

    List<CourseSummary> getCourseList();
}
