package com.ohgiraffers.lxp.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record WithdrawMemberRequest(
        @NotBlank
        String password
) {
}
