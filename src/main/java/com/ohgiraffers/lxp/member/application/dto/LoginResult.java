package com.ohgiraffers.lxp.member.application.dto;

import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;

public record LoginResult(
        Long memberId,
        String email,
        String nickname,
        MemberRole role,
        MemberStatus status
) {

    public static LoginResult from(Member member) {
        return new LoginResult(
                member.getId(),
                member.getEmail().value(),
                member.getNickname().value(),
                member.getRole(),
                member.getStatus()
        );
    }
}
