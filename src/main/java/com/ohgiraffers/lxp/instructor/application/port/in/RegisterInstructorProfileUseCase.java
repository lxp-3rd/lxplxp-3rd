package com.ohgiraffers.lxp.instructor.application.port.in;

import com.ohgiraffers.lxp.instructor.application.port.command.RegisterInstructorProfileCommand;

public interface RegisterInstructorProfileUseCase {

    void register(RegisterInstructorProfileCommand command);
}
