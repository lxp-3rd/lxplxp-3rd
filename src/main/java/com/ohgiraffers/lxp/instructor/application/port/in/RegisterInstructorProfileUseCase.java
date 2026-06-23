package com.ohgiraffers.lxp.instructor.application.port.in;

public interface RegisterInstructorProfileUseCase {

    void register(RegisterInstructorProfileCommand command);

    record RegisterInstructorProfileCommand(
            Long instructorId,
            String profileImageUrl,
            String bio
    ) {}
}
