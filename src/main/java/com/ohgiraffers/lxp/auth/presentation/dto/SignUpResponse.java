package com.ohgiraffers.lxp.auth.presentation.dto;

import com.ohgiraffers.lxp.member.application.dto.SignUpResult;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;

public record SignUpResponse(
        Long memberId,
        String email,
        String nickname,
        MemberRole role,
        MemberStatus status
) {

    public static SignUpResponse from(SignUpResult result) {
        return new SignUpResponse(
                result.memberId(),
                result.email(),
                result.nickname(),
                result.role(),
                result.status()
        );
    }
}
