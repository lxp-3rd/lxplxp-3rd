package com.ohgiraffers.lxp.instructor.application.port.in;

import com.ohgiraffers.lxp.instructor.application.port.command.ApplyInstructorCommand;

public interface ApplyInstructorUseCase {

    void apply(ApplyInstructorCommand command);
}
