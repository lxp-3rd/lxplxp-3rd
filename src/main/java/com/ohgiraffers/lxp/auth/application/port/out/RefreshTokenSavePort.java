package com.ohgiraffers.lxp.auth.application.port.out;

import java.time.Instant;

public interface RefreshTokenSavePort {

    void save(Long memberId, String refreshToken, Instant expiresAt);
}
