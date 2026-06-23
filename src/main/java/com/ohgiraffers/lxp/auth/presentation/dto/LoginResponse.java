package com.ohgiraffers.lxp.auth.presentation.dto;

import com.ohgiraffers.lxp.member.application.dto.LoginResult;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;

public record LoginResponse(
        Long memberId,
        String email,
        String nickname,
        MemberRole role,
        MemberStatus status,
        String accessToken,
        String refreshToken
) {

    public static LoginResponse from(LoginResult result) {
        return new LoginResponse(
                result.memberId(),
                result.email(),
                result.nickname(),
                result.role(),
                result.status(),
                result.accessToken(),
                result.refreshToken()
        );
    }
}
