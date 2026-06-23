package com.ohgiraffers.lxp.auth.infrastructure.token;

import com.ohgiraffers.lxp.auth.application.dto.TokenPair;
import com.ohgiraffers.lxp.auth.application.port.out.TokenIssuePort;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenIssueAdapter implements TokenIssuePort {

    private static final String ACCESS_TOKEN_TYPE = "ACCESS";
    private static final String REFRESH_TOKEN_TYPE = "REFRESH";

    private final SecretKey secretKey;
    private final Duration accessTokenExpiration;
    private final Duration refreshTokenExpiration;

    public JwtTokenIssueAdapter(
            @Value("${lxp.auth.jwt.secret:lxp-jwt-secret-key-for-local-development-1234567890}") String secret,
            @Value("${lxp.auth.jwt.access-token-expiration-minutes:30}") long accessTokenExpirationMinutes,
            @Value("${lxp.auth.jwt.refresh-token-expiration-days:14}") long refreshTokenExpirationDays
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = Duration.ofMinutes(accessTokenExpirationMinutes);
        this.refreshTokenExpiration = Duration.ofDays(refreshTokenExpirationDays);
    }

    @Override
    public TokenPair issue(Long memberId, MemberRole role) {
        Instant now = Instant.now();
        Instant accessTokenExpiresAt = now.plus(accessTokenExpiration);
        Instant refreshTokenExpiresAt = now.plus(refreshTokenExpiration);

        return new TokenPair(
                createToken(memberId, role, ACCESS_TOKEN_TYPE, now, accessTokenExpiresAt),
                createToken(memberId, role, REFRESH_TOKEN_TYPE, now, refreshTokenExpiresAt),
                refreshTokenExpiresAt
        );
    }

    private String createToken(Long memberId, MemberRole role, String tokenType, Instant issuedAt, Instant expiresAt) {
        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim("memberId", memberId)
                .claim("role", role.name())
                .claim("type", tokenType)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();
    }
}
