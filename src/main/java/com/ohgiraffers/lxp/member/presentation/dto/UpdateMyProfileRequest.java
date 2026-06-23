package com.ohgiraffers.lxp.member.presentation.dto;

import com.ohgiraffers.lxp.member.application.port.command.UpdateMyProfileCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateMyProfileRequest(
        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 30, message = "닉네임은 30자 이하여야 합니다.")
        String nickname
) {

    public UpdateMyProfileCommand toCommand(Long memberId) {
        return new UpdateMyProfileCommand(memberId, nickname);
    }
}
