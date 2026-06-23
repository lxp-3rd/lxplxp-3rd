package com.ohgiraffers.lxp.auth.application.port.command;

public record ReissueTokenCommand(
        String refreshToken
) {
}
