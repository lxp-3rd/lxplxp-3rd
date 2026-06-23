package com.ohgiraffers.lxp.member.application.port.command;

import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;

public record AdminMemberDetailCommand(
        Long requesterId,
        MemberRole requesterRole,
        Long memberId
) {
}
