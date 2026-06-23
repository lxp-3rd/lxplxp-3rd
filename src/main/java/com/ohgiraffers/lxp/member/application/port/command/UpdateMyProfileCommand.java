package com.ohgiraffers.lxp.member.application.port.command;

public record UpdateMyProfileCommand(
        Long memberId,
        String nickname
) {
}
