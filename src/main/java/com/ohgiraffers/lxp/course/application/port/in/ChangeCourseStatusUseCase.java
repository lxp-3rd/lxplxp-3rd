package com.ohgiraffers.lxp.course.application.port.in;

import com.ohgiraffers.lxp.course.application.port.command.ChangeCourseStatusCommand;

public interface ChangeCourseStatusUseCase {
    void changeStatus(ChangeCourseStatusCommand command);
}
