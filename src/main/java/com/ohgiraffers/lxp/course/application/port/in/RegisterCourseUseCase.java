package com.ohgiraffers.lxp.course.application.port.in;

import com.ohgiraffers.lxp.course.application.port.command.RegisterCourseCommand;

public interface RegisterCourseUseCase {

    Long register(RegisterCourseCommand command);
}
