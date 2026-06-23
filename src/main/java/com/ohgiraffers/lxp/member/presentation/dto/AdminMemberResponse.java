package com.ohgiraffers.lxp.member.presentation.dto;

import com.ohgiraffers.lxp.member.application.dto.AdminMemberResult;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;

import java.time.LocalDateTime;

public record AdminMemberResponse(
        Long memberId,
        String email,
        String nickname,
        MemberRole role,
        MemberStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static AdminMemberResponse from(AdminMemberResult result) {
        return new AdminMemberResponse(
                result.memberId(),
                result.email(),
                result.nickname(),
                result.role(),
                result.status(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
