package com.ohgiraffers.lxp.instructor.application.port.in;

import com.ohgiraffers.lxp.instructor.application.port.command.UpdateInstructorProfileCommand;

public interface UpdateInstructorProfileUseCase {

    void update(UpdateInstructorProfileCommand command);
}
