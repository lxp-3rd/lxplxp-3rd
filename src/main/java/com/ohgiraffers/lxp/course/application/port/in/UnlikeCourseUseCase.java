package com.ohgiraffers.lxp.course.application.port.in;

import com.ohgiraffers.lxp.course.application.port.command.UnlikeCourseCommand;

public interface UnlikeCourseUseCase {
    void unlike(UnlikeCourseCommand command);
}
