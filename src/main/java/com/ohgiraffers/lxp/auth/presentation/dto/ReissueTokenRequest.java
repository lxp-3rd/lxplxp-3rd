package com.ohgiraffers.lxp.auth.presentation.dto;

import com.ohgiraffers.lxp.auth.application.port.command.ReissueTokenCommand;
import jakarta.validation.constraints.NotBlank;

public record ReissueTokenRequest(
        @NotBlank(message = "리프레시 토큰은 필수입니다.")
        String refreshToken
) {

    public ReissueTokenCommand toCommand() {
        return new ReissueTokenCommand(refreshToken);
    }
}
