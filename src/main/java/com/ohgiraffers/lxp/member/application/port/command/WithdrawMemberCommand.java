package com.ohgiraffers.lxp.member.application.port.command;

public record WithdrawMemberCommand(
        Long memberId,
        String password
) {
}
