package com.ohgiraffers.lxp.instructor.application.port.in;

public interface UpdateInstructorProfileUseCase {

    void update(UpdateInstructorProfileCommand command);

    record UpdateInstructorProfileCommand(
            Long instructorId,
            String profileImageUrl,
            String bio
    ) {}
}
