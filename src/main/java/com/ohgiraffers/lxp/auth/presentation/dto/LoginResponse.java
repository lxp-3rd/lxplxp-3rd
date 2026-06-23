package com.ohgiraffers.lxp.auth.presentation.dto;

import com.ohgiraffers.lxp.member.application.dto.LoginResult;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {

    public static LoginResponse from(LoginResult result) {
        return new LoginResponse(
                result.accessToken(),
                result.refreshToken()
        );
    }
}
