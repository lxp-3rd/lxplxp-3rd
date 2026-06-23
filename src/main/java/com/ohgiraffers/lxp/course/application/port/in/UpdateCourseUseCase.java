package com.ohgiraffers.lxp.course.application.port.in;

import com.ohgiraffers.lxp.course.application.port.command.UpdateCourseCommand;

public interface UpdateCourseUseCase {
    void update(UpdateCourseCommand command);
}
