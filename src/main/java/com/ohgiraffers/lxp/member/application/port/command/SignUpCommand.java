package com.ohgiraffers.lxp.member.application.port.command;

public record SignUpCommand(
        String email,
        String nickname,
        String password,
        String passwordConfirm
) {
}
