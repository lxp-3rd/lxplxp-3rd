package com.ohgiraffers.lxp.instructor.application.port.in;

public interface ApplyInstructorUseCase {

    void apply(ApplyInstructorCommand command);

    record ApplyInstructorCommand(
            Long memberId,
            String instructorName,
            String introduction,
            String expertise
    ) {
    }
}
