package com.ohgiraffers.lxp.auth.application.service;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.application.dto.RefreshTokenInfo;
import com.ohgiraffers.lxp.auth.application.dto.ReissueTokenResult;
import com.ohgiraffers.lxp.auth.application.port.command.LogoutCommand;
import com.ohgiraffers.lxp.auth.application.port.command.ReissueTokenCommand;
import com.ohgiraffers.lxp.auth.application.port.in.LogoutUseCase;
import com.ohgiraffers.lxp.auth.application.port.in.ReissueTokenUseCase;
import com.ohgiraffers.lxp.auth.application.port.out.AccessTokenIssuePort;
import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenQueryPort;
import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenRevokePort;
import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenValidatePort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AuthTokenService implements ReissueTokenUseCase, LogoutUseCase {

    private final RefreshTokenValidatePort refreshTokenValidatePort;
    private final RefreshTokenQueryPort refreshTokenQueryPort;
    private final RefreshTokenRevokePort refreshTokenRevokePort;
    private final AccessTokenIssuePort accessTokenIssuePort;

    public AuthTokenService(
            RefreshTokenValidatePort refreshTokenValidatePort,
            RefreshTokenQueryPort refreshTokenQueryPort,
            RefreshTokenRevokePort refreshTokenRevokePort,
            AccessTokenIssuePort accessTokenIssuePort
    ) {
        this.refreshTokenValidatePort = refreshTokenValidatePort;
        this.refreshTokenQueryPort = refreshTokenQueryPort;
        this.refreshTokenRevokePort = refreshTokenRevokePort;
        this.accessTokenIssuePort = accessTokenIssuePort;
    }

    @Override
    @Transactional(readOnly = true)
    public ReissueTokenResult reissue(ReissueTokenCommand command) {
        AuthenticatedMember authenticatedMember = refreshTokenValidatePort.validateRefreshToken(command.refreshToken());
        RefreshTokenInfo refreshTokenInfo = getUsableRefreshToken(command.refreshToken(), authenticatedMember.memberId());

        if (refreshTokenInfo.isExpired(Instant.now())) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }

        return new ReissueTokenResult(
                accessTokenIssuePort.issueAccessToken(authenticatedMember.memberId(), authenticatedMember.role().name())
        );
    }

    @Override
    @Transactional
    public void logout(LogoutCommand command) {
        AuthenticatedMember authenticatedMember = refreshTokenValidatePort.validateRefreshToken(command.refreshToken());
        RefreshTokenInfo refreshTokenInfo = getUsableRefreshToken(command.refreshToken(), command.memberId());

        if (!authenticatedMember.memberId().equals(command.memberId())) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }
        if (refreshTokenInfo.isExpired(Instant.now())) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }

        refreshTokenRevokePort.revoke(command.refreshToken());
    }

    private RefreshTokenInfo getUsableRefreshToken(String refreshToken, Long memberId) {
        RefreshTokenInfo refreshTokenInfo = refreshTokenQueryPort.findByToken(refreshToken)
                .orElseThrow(() -> new BusinessException(ErrorCode.TOKEN_INVALID));

        if (!refreshTokenInfo.memberId().equals(memberId)) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }
        if (refreshTokenInfo.revoked()) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }
        return refreshTokenInfo;
    }
}
