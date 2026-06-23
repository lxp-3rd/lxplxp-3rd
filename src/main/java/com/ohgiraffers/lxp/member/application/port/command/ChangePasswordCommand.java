package com.ohgiraffers.lxp.member.application.port.command;

public record ChangePasswordCommand(
        Long memberId,
        String currentPassword,
        String newPassword,
        String newPasswordConfirm
) {
}
