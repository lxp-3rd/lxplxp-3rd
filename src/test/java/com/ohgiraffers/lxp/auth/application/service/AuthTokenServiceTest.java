package com.ohgiraffers.lxp.auth.application.service;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.application.dto.RefreshTokenInfo;
import com.ohgiraffers.lxp.auth.application.dto.ReissueTokenResult;
import com.ohgiraffers.lxp.auth.application.port.command.LogoutCommand;
import com.ohgiraffers.lxp.auth.application.port.command.ReissueTokenCommand;
import com.ohgiraffers.lxp.auth.application.port.out.AccessTokenIssuePort;
import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenQueryPort;
import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenRevokePort;
import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenValidatePort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthTokenServiceTest {

    private static final String REFRESH_TOKEN = "refresh-token";
    private static final Instant FUTURE_EXPIRES_AT = Instant.now().plusSeconds(3600);

    @Test
    void reissue_success() {
        RefreshTokenValidatePortStub validatePort = new RefreshTokenValidatePortStub(new AuthenticatedMember(1L, MemberRole.LEARNER));
        RefreshTokenQueryPortStub queryPort = new RefreshTokenQueryPortStub(new RefreshTokenInfo(1L, FUTURE_EXPIRES_AT, false));
        AccessTokenIssuePortStub accessTokenIssuePort = new AccessTokenIssuePortStub();
        AuthTokenService service = new AuthTokenService(
                validatePort,
                queryPort,
                new RefreshTokenRevokePortStub(),
                accessTokenIssuePort
        );

        ReissueTokenResult result = service.reissue(new ReissueTokenCommand(REFRESH_TOKEN));

        assertThat(result.accessToken()).isEqualTo("new-access-token");
        assertThat(accessTokenIssuePort.issuedMemberId).isEqualTo(1L);
        assertThat(accessTokenIssuePort.issuedRole).isEqualTo(MemberRole.LEARNER);
    }

    @Test
    void reissue_fails_when_refresh_token_is_not_saved() {
        AuthTokenService service = new AuthTokenService(
                new RefreshTokenValidatePortStub(new AuthenticatedMember(1L, MemberRole.LEARNER)),
                new RefreshTokenQueryPortStub(null),
                new RefreshTokenRevokePortStub(),
                new AccessTokenIssuePortStub()
        );

        assertThatThrownBy(() -> service.reissue(new ReissueTokenCommand(REFRESH_TOKEN)))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.TOKEN_INVALID);
    }

    @Test
    void reissue_fails_when_refresh_token_is_revoked() {
        AuthTokenService service = new AuthTokenService(
                new RefreshTokenValidatePortStub(new AuthenticatedMember(1L, MemberRole.LEARNER)),
                new RefreshTokenQueryPortStub(new RefreshTokenInfo(1L, FUTURE_EXPIRES_AT, true)),
                new RefreshTokenRevokePortStub(),
                new AccessTokenIssuePortStub()
        );

        assertThatThrownBy(() -> service.reissue(new ReissueTokenCommand(REFRESH_TOKEN)))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.TOKEN_INVALID);
    }

    @Test
    void reissue_fails_when_refresh_token_is_expired() {
        AuthTokenService service = new AuthTokenService(
                new RefreshTokenValidatePortStub(new AuthenticatedMember(1L, MemberRole.LEARNER)),
                new RefreshTokenQueryPortStub(new RefreshTokenInfo(1L, Instant.now().minusSeconds(60), false)),
                new RefreshTokenRevokePortStub(),
                new AccessTokenIssuePortStub()
        );

        assertThatThrownBy(() -> service.reissue(new ReissueTokenCommand(REFRESH_TOKEN)))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.TOKEN_EXPIRED);
    }

    @Test
    void logout_success() {
        RefreshTokenRevokePortStub revokePort = new RefreshTokenRevokePortStub();
        AuthTokenService service = new AuthTokenService(
                new RefreshTokenValidatePortStub(new AuthenticatedMember(1L, MemberRole.LEARNER)),
                new RefreshTokenQueryPortStub(new RefreshTokenInfo(1L, FUTURE_EXPIRES_AT, false)),
                revokePort,
                new AccessTokenIssuePortStub()
        );

        service.logout(new LogoutCommand(1L, REFRESH_TOKEN));

        assertThat(revokePort.revokedRefreshToken).isEqualTo(REFRESH_TOKEN);
    }

    @Test
    void logout_fails_when_refresh_token_member_is_different_from_login_member() {
        AuthTokenService service = new AuthTokenService(
                new RefreshTokenValidatePortStub(new AuthenticatedMember(2L, MemberRole.LEARNER)),
                new RefreshTokenQueryPortStub(new RefreshTokenInfo(1L, FUTURE_EXPIRES_AT, false)),
                new RefreshTokenRevokePortStub(),
                new AccessTokenIssuePortStub()
        );

        assertThatThrownBy(() -> service.logout(new LogoutCommand(1L, REFRESH_TOKEN)))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.TOKEN_INVALID);
    }

    private record RefreshTokenValidatePortStub(AuthenticatedMember authenticatedMember) implements RefreshTokenValidatePort {

        @Override
        public AuthenticatedMember validateRefreshToken(String token) {
            return authenticatedMember;
        }
    }

    private record RefreshTokenQueryPortStub(RefreshTokenInfo refreshTokenInfo) implements RefreshTokenQueryPort {

        @Override
        public Optional<RefreshTokenInfo> findByToken(String refreshToken) {
            return Optional.ofNullable(refreshTokenInfo);
        }
    }

    private static class RefreshTokenRevokePortStub implements RefreshTokenRevokePort {

        private String revokedRefreshToken;

        @Override
        public void revoke(String refreshToken) {
            this.revokedRefreshToken = refreshToken;
        }
    }

    private static class AccessTokenIssuePortStub implements AccessTokenIssuePort {

        private Long issuedMemberId;
        private MemberRole issuedRole;

        @Override
        public String issueAccessToken(Long memberId, MemberRole role) {
            this.issuedMemberId = memberId;
            this.issuedRole = role;
            return "new-access-token";
        }
    }
}
