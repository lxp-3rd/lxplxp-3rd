package com.ohgiraffers.lxp.instructor.presentation;

import com.ohgiraffers.lxp.instructor.application.port.in.ReviewAction;
import com.ohgiraffers.lxp.instructor.application.port.in.ReviewInstructorApplicationCommand;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public record ReviewInstructorApplicationRequest(

        @NotNull(message = "심사 결과는 필수입니다.")
        ReviewAction action,

        String rejectionReason
) {
    @AssertTrue(message = "REJECT 시 반려 사유는 필수입니다.")
    public boolean isRejectionReasonValidForReject() {
        return action != ReviewAction.REJECT
                || (rejectionReason != null && !rejectionReason.isBlank());
    }

    public ReviewInstructorApplicationCommand toCommand(Long applicationId) {
        return new ReviewInstructorApplicationCommand(applicationId, action, rejectionReason);
    }
}
