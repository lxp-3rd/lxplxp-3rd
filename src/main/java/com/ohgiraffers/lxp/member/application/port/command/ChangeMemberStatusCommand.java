package com.ohgiraffers.lxp.member.application.port.command;

import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;

public record ChangeMemberStatusCommand(
        Long requesterId,
        MemberRole requesterRole,
        Long memberId,
        MemberStatus status
) {
}
