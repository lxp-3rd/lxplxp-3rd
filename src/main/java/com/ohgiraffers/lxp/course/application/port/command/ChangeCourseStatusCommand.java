package com.ohgiraffers.lxp.course.application.port.command;

import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.entity.HiddenBy;

public record ChangeCourseStatusCommand(Long courseId, CourseStatus newStatus, HiddenBy changedBy) {
}
