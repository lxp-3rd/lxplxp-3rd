package com.ohgiraffers.lxp.auth.application.dto;

import java.time.Instant;

public record TokenPair(
        String accessToken,
        String refreshToken,
        Instant refreshTokenExpiresAt
) {
}
