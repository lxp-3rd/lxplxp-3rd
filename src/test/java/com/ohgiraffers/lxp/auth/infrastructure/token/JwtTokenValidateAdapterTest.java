package com.ohgiraffers.lxp.auth.infrastructure.token;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.application.dto.TokenPair;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenValidateAdapterTest {

    private static final String SECRET = "lxp-jwt-secret-key-for-test-environment-1234567890";

    @Test
    void validate_access_token_success() {
        JwtTokenIssueAdapter issueAdapter = new JwtTokenIssueAdapter(SECRET, 30, 14);
        JwtTokenValidateAdapter validateAdapter = new JwtTokenValidateAdapter(SECRET);
        TokenPair tokenPair = issueAdapter.issue(1L, MemberRole.LEARNER);

        AuthenticatedMember authenticatedMember = validateAdapter.validateAccessToken(tokenPair.accessToken());

        assertThat(authenticatedMember.memberId()).isEqualTo(1L);
        assertThat(authenticatedMember.role()).isEqualTo(MemberRole.LEARNER);
    }

    @Test
    void validate_access_token_fails_when_token_is_refresh_token() {
        JwtTokenIssueAdapter issueAdapter = new JwtTokenIssueAdapter(SECRET, 30, 14);
        JwtTokenValidateAdapter validateAdapter = new JwtTokenValidateAdapter(SECRET);
        TokenPair tokenPair = issueAdapter.issue(1L, MemberRole.LEARNER);

        assertThatThrownBy(() -> validateAdapter.validateAccessToken(tokenPair.refreshToken()))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_TYPE_MISMATCH.getMessage());
    }

    @Test
    void validate_refresh_token_success() {
        JwtTokenIssueAdapter issueAdapter = new JwtTokenIssueAdapter(SECRET, 30, 14);
        JwtTokenValidateAdapter validateAdapter = new JwtTokenValidateAdapter(SECRET);
        TokenPair tokenPair = issueAdapter.issue(1L, MemberRole.LEARNER);

        AuthenticatedMember authenticatedMember = validateAdapter.validateRefreshToken(tokenPair.refreshToken());

        assertThat(authenticatedMember.memberId()).isEqualTo(1L);
        assertThat(authenticatedMember.role()).isEqualTo(MemberRole.LEARNER);
    }

    @Test
    void validate_refresh_token_fails_when_token_is_access_token() {
        JwtTokenIssueAdapter issueAdapter = new JwtTokenIssueAdapter(SECRET, 30, 14);
        JwtTokenValidateAdapter validateAdapter = new JwtTokenValidateAdapter(SECRET);
        TokenPair tokenPair = issueAdapter.issue(1L, MemberRole.LEARNER);

        assertThatThrownBy(() -> validateAdapter.validateRefreshToken(tokenPair.accessToken()))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_TYPE_MISMATCH.getMessage());
    }

    @Test
    void validate_access_token_fails_when_token_is_expired() {
        JwtTokenValidateAdapter validateAdapter = new JwtTokenValidateAdapter(SECRET);
        String expiredToken = createToken(JwtTokenType.ACCESS, Instant.now().minusSeconds(120), Instant.now().minusSeconds(60));

        assertThatThrownBy(() -> validateAdapter.validateAccessToken(expiredToken))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_EXPIRED.getMessage());
    }

    @Test
    void validate_access_token_fails_when_token_is_invalid() {
        JwtTokenValidateAdapter validateAdapter = new JwtTokenValidateAdapter(SECRET);

        assertThatThrownBy(() -> validateAdapter.validateAccessToken("invalid-token"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_INVALID.getMessage());
    }

    @Test
    void validate_access_token_fails_when_role_claim_is_invalid() {
        JwtTokenValidateAdapter validateAdapter = new JwtTokenValidateAdapter(SECRET);
        String invalidRoleToken = createToken(JwtTokenType.ACCESS, "UNKNOWN", Instant.now(), Instant.now().plusSeconds(60));

        assertThatThrownBy(() -> validateAdapter.validateAccessToken(invalidRoleToken))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_INVALID.getMessage());
    }

    private String createToken(String tokenType, Instant issuedAt, Instant expiresAt) {
        return createToken(tokenType, MemberRole.LEARNER.name(), issuedAt, expiresAt);
    }

    private String createToken(String tokenType, String role, Instant issuedAt, Instant expiresAt) {
        SecretKey secretKey = JwtSecretKeyFactory.create(SECRET);
        return Jwts.builder()
                .subject("1")
                .claim("memberId", 1L)
                .claim("role", role)
                .claim("type", tokenType)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();
    }
}
