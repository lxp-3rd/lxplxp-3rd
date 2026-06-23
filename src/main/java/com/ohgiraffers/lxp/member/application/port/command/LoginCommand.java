package com.ohgiraffers.lxp.member.application.port.command;

public record LoginCommand(
        String email,
        String password
) {
}
