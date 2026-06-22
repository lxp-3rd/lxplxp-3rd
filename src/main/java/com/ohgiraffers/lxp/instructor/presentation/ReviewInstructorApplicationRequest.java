package com.ohgiraffers.lxp.instructor.presentation;

import com.ohgiraffers.lxp.instructor.application.port.in.ReviewInstructorApplicationUseCase.ReviewAction;
import com.ohgiraffers.lxp.instructor.application.port.in.ReviewInstructorApplicationUseCase.ReviewInstructorApplicationCommand;
import jakarta.validation.constraints.NotNull;

public record ReviewInstructorApplicationRequest(

        @NotNull(message = "심사 결과는 필수입니다.")
        ReviewAction action,

        String rejectionReason
) {
    public ReviewInstructorApplicationCommand toCommand(Long applicationId) {
        return new ReviewInstructorApplicationCommand(applicationId, action, rejectionReason);
    }
}
