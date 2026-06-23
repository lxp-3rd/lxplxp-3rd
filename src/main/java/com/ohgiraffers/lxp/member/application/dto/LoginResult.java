package com.ohgiraffers.lxp.member.application.dto;

import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;

public record LoginResult(
        Long memberId,
        String email,
        String nickname,
        MemberRole role,
        MemberStatus status,
        String accessToken,
        String refreshToken
) {

    public static LoginResult from(Member member, String accessToken, String refreshToken) {
        return new LoginResult(
                member.getId(),
                member.getEmail().value(),
                member.getNickname().value(),
                member.getRole(),
                member.getStatus(),
                accessToken,
                refreshToken
        );
    }
}
