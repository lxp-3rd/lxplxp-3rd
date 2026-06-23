package com.ohgiraffers.lxp.member.application.dto;

import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;

public record MemberProfileResult(
        Long memberId,
        String email,
        String nickname,
        MemberRole role,
        MemberStatus status
) {

    public static MemberProfileResult from(Member member) {
        return new MemberProfileResult(
                member.getId(),
                member.getEmail().value(),
                member.getNickname().value(),
                member.getRole(),
                member.getStatus()
        );
    }
}
