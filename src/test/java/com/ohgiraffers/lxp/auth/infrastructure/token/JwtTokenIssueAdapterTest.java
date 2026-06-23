package com.ohgiraffers.lxp.auth.infrastructure.token;

import com.ohgiraffers.lxp.auth.application.dto.TokenPair;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenIssueAdapterTest {

    private static final String SECRET = "lxp-jwt-secret-key-for-test-environment-1234567890";

    @Test
    void issue_creates_access_and_refresh_token_with_member_claims() {
        JwtTokenIssueAdapter tokenIssueAdapter = new JwtTokenIssueAdapter(SECRET, 30, 14);

        TokenPair tokenPair = tokenIssueAdapter.issue(1L, MemberRole.LEARNER);

        Claims accessTokenClaims = parse(tokenPair.accessToken());
        Claims refreshTokenClaims = parse(tokenPair.refreshToken());

        assertThat(accessTokenClaims.getSubject()).isEqualTo("1");
        assertThat(accessTokenClaims.get("memberId", Long.class)).isEqualTo(1L);
        assertThat(accessTokenClaims.get("role", String.class)).isEqualTo("LEARNER");
        assertThat(accessTokenClaims.get("type", String.class)).isEqualTo("ACCESS");
        assertThat(refreshTokenClaims.getSubject()).isEqualTo("1");
        assertThat(refreshTokenClaims.get("memberId", Long.class)).isEqualTo(1L);
        assertThat(refreshTokenClaims.get("role", String.class)).isEqualTo("LEARNER");
        assertThat(refreshTokenClaims.get("type", String.class)).isEqualTo("REFRESH");
        assertThat(tokenPair.refreshTokenExpiresAt()).isNotNull();
    }

    private Claims parse(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
