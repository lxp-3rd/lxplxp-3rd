package com.ohgiraffers.lxp.auth.application.dto;

import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;

public record AuthenticatedMember(
        Long memberId,
        MemberRole role
) {

    public static final String REQUEST_ATTRIBUTE_NAME = "authenticatedMember";
}
