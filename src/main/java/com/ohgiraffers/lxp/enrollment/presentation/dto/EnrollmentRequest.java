package com.ohgiraffers.lxp.enrollment.presentation.dto;

import com.ohgiraffers.lxp.enrollment.application.port.command.EnrollCommand;
import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(
        @NotNull Long memberId,
        @NotNull Long courseId
) {

    public EnrollCommand toCommand() {
        return new EnrollCommand(memberId, courseId);
    }
}
