package com.ohgiraffers.lxp.auth.application.dto;

import java.time.Instant;

public record RefreshTokenInfo(
        Long memberId,
        Instant expiresAt,
        boolean revoked
) {

    public boolean isExpired(Instant now) {
        return !expiresAt.isAfter(now);
    }
}
