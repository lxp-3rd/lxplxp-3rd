package com.ohgiraffers.lxp.member.application.dto;

import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;

import java.time.LocalDateTime;

public record AdminMemberResult(
        Long memberId,
        String email,
        String nickname,
        MemberRole role,
        MemberStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
