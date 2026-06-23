package com.ohgiraffers.lxp.auth.infrastructure.token;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

final class JwtSecretKeyFactory {

    private JwtSecretKeyFactory() {
    }

    static SecretKey create(String secret) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("lxp.auth.jwt.secret 설정이 필요합니다.");
        }
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
