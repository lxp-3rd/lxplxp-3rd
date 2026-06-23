package com.ohgiraffers.lxp.auth.presentation.dto;

import com.ohgiraffers.lxp.auth.application.port.command.LogoutCommand;
import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(
        @NotBlank(message = "리프레시 토큰은 필수입니다.")
        String refreshToken
) {

    public LogoutCommand toCommand(Long memberId) {
        return new LogoutCommand(memberId, refreshToken);
    }
}
