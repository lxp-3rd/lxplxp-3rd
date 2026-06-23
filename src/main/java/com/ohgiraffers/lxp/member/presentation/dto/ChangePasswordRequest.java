package com.ohgiraffers.lxp.member.presentation.dto;

import com.ohgiraffers.lxp.auth.presentation.validation.PasswordMaxByteLength;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank
        String currentPassword,

        @NotBlank
        @Size(min = 8)
        @PasswordMaxByteLength
        String newPassword,

        @NotBlank
        String newPasswordConfirm
) {
}
