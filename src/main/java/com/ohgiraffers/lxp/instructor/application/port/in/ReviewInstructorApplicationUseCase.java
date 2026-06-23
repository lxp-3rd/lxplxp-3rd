package com.ohgiraffers.lxp.instructor.application.port.in;

import com.ohgiraffers.lxp.instructor.application.port.command.ReviewInstructorApplicationCommand;

public interface ReviewInstructorApplicationUseCase {

    void review(ReviewInstructorApplicationCommand command);
}
