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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AuthTokenServiceTest {

    private static final String REFRESH_TOKEN = "refresh-token";
    private static final Instant FUTURE_EXPIRES_AT = Instant.now().plusSeconds(3600);

    @Mock
    private RefreshTokenValidatePort refreshTokenValidatePort;

    @Mock
    private RefreshTokenQueryPort refreshTokenQueryPort;

    @Mock
    private RefreshTokenRevokePort refreshTokenRevokePort;

    @Mock
    private AccessTokenIssuePort accessTokenIssuePort;

    @InjectMocks
    private AuthTokenService service;

    @Test
    void reissue_success() {
        given(refreshTokenValidatePort.validateRefreshToken(REFRESH_TOKEN))
                .willReturn(new AuthenticatedMember(1L, MemberRole.LEARNER));
        given(refreshTokenQueryPort.findByToken(REFRESH_TOKEN))
                .willReturn(Optional.of(new RefreshTokenInfo(1L, FUTURE_EXPIRES_AT, false)));
        given(accessTokenIssuePort.issueAccessToken(1L, MemberRole.LEARNER.name()))
                .willReturn("new-access-token");

        ReissueTokenResult result = service.reissue(new ReissueTokenCommand(REFRESH_TOKEN));

        assertThat(result.accessToken()).isEqualTo("new-access-token");
        then(accessTokenIssuePort).should().issueAccessToken(1L, MemberRole.LEARNER.name());
    }

    @Test
    void reissue_fails_when_refresh_token_is_not_saved() {
        given(refreshTokenValidatePort.validateRefreshToken(REFRESH_TOKEN))
                .willReturn(new AuthenticatedMember(1L, MemberRole.LEARNER));
        given(refreshTokenQueryPort.findByToken(REFRESH_TOKEN))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> service.reissue(new ReissueTokenCommand(REFRESH_TOKEN)))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_INVALID.getMessage());
    }

    @Test
    void reissue_fails_when_refresh_token_is_revoked() {
        given(refreshTokenValidatePort.validateRefreshToken(REFRESH_TOKEN))
                .willReturn(new AuthenticatedMember(1L, MemberRole.LEARNER));
        given(refreshTokenQueryPort.findByToken(REFRESH_TOKEN))
                .willReturn(Optional.of(new RefreshTokenInfo(1L, FUTURE_EXPIRES_AT, true)));

        assertThatThrownBy(() -> service.reissue(new ReissueTokenCommand(REFRESH_TOKEN)))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_INVALID.getMessage());
    }

    @Test
    void reissue_fails_when_refresh_token_is_expired() {
        given(refreshTokenValidatePort.validateRefreshToken(REFRESH_TOKEN))
                .willReturn(new AuthenticatedMember(1L, MemberRole.LEARNER));
        given(refreshTokenQueryPort.findByToken(REFRESH_TOKEN))
                .willReturn(Optional.of(new RefreshTokenInfo(1L, Instant.now().minusSeconds(60), false)));

        assertThatThrownBy(() -> service.reissue(new ReissueTokenCommand(REFRESH_TOKEN)))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_EXPIRED.getMessage());
    }

    @Test
    void logout_success() {
        given(refreshTokenValidatePort.validateRefreshToken(REFRESH_TOKEN))
                .willReturn(new AuthenticatedMember(1L, MemberRole.LEARNER));
        given(refreshTokenQueryPort.findByToken(REFRESH_TOKEN))
                .willReturn(Optional.of(new RefreshTokenInfo(1L, FUTURE_EXPIRES_AT, false)));

        service.logout(new LogoutCommand(1L, REFRESH_TOKEN));

        then(refreshTokenRevokePort).should().revoke(REFRESH_TOKEN);
    }

    @Test
    void logout_fails_when_refresh_token_member_is_different_from_login_member() {
        given(refreshTokenValidatePort.validateRefreshToken(REFRESH_TOKEN))
                .willReturn(new AuthenticatedMember(2L, MemberRole.LEARNER));
        given(refreshTokenQueryPort.findByToken(REFRESH_TOKEN))
                .willReturn(Optional.of(new RefreshTokenInfo(1L, FUTURE_EXPIRES_AT, false)));

        assertThatThrownBy(() -> service.logout(new LogoutCommand(1L, REFRESH_TOKEN)))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.TOKEN_INVALID.getMessage());
    }
}
