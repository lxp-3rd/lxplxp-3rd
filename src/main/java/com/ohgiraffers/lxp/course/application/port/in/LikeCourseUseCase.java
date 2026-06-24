package com.ohgiraffers.lxp.course.application.port.in;

import com.ohgiraffers.lxp.course.application.port.command.LikeCourseCommand;

public interface LikeCourseUseCase {
    void like(LikeCourseCommand command);
}
