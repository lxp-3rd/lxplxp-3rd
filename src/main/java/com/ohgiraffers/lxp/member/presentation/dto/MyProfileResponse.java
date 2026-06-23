package com.ohgiraffers.lxp.member.presentation.dto;

import com.ohgiraffers.lxp.member.application.dto.MemberProfileResult;

public record MyProfileResponse(
        Long memberId,
        String email,
        String nickname,
        String role,
        String status
) {

    public static MyProfileResponse from(MemberProfileResult result) {
        return new MyProfileResponse(
                result.memberId(),
                result.email(),
                result.nickname(),
                result.role().name(),
                result.status().name()
        );
    }
}
