package com.ohgiraffers.lxp.auth.application.port.command;

public record LogoutCommand(
        Long memberId,
        String refreshToken
) {
}
