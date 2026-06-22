package com.ohgiraffers.lxp.enrollment.presentation.dto;

import com.ohgiraffers.lxp.enrollment.application.port.command.EnrollCommand;
import jakarta.validation.constraints.NotNull;

/**
 * 수강신청 요청. memberId는 현재 요청 body로 받는다
 */
public record EnrollmentRequest(
        @NotNull Long memberId,
        @NotNull Long courseId
) {

    public EnrollCommand toCommand() {
        return new EnrollCommand(memberId, courseId);
    }
}
