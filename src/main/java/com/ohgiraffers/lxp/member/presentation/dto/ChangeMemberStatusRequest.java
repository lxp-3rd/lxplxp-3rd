package com.ohgiraffers.lxp.member.presentation.dto;

import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeMemberStatusRequest(
        @NotNull
        MemberStatus status
) {
}
