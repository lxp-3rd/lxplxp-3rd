package com.ohgiraffers.lxp.instructor.application.port.in;

public interface ReviewInstructorApplicationUseCase {

    void review(ReviewInstructorApplicationCommand command);

    record ReviewInstructorApplicationCommand(
            Long applicationId,
            ReviewAction action,
            String rejectionReason
    ) {}

    enum ReviewAction {
        APPROVE, REJECT
    }
}
