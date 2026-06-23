package com.ohgiraffers.lxp.member.application.service;

import com.ohgiraffers.lxp.auth.application.dto.TokenPair;
import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenSavePort;
import com.ohgiraffers.lxp.auth.application.port.out.TokenIssuePort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.application.dto.LoginResult;
import com.ohgiraffers.lxp.member.application.port.command.LoginCommand;
import com.ohgiraffers.lxp.member.application.port.in.LoginUseCase;
import com.ohgiraffers.lxp.member.application.port.out.MemberRepositoryPort;
import com.ohgiraffers.lxp.member.application.port.out.PasswordMatchPort;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService implements LoginUseCase {

    private final MemberRepositoryPort memberRepositoryPort;
    private final PasswordMatchPort passwordMatchPort;
    private final TokenIssuePort tokenIssuePort;
    private final RefreshTokenSavePort refreshTokenSavePort;

    public LoginService(
            MemberRepositoryPort memberRepositoryPort,
            PasswordMatchPort passwordMatchPort,
            TokenIssuePort tokenIssuePort,
            RefreshTokenSavePort refreshTokenSavePort
    ) {
        this.memberRepositoryPort = memberRepositoryPort;
        this.passwordMatchPort = passwordMatchPort;
        this.tokenIssuePort = tokenIssuePort;
        this.refreshTokenSavePort = refreshTokenSavePort;
    }

    @Override
    @Transactional
    public LoginResult login(LoginCommand command) {
        Member member = memberRepositoryPort.findByEmail(new Email(command.email()))
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordMatchPort.matches(command.password(), member.getPassword().value())) {
            throw new BusinessException(ErrorCode.MEMBER_INVALID_PASSWORD);
        }

        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_ACTIVE);
        }

        TokenPair tokenPair = tokenIssuePort.issue(member.getId(), member.getRole());
        refreshTokenSavePort.save(member.getId(), tokenPair.refreshToken(), tokenPair.refreshTokenExpiresAt());

        return LoginResult.from(member, tokenPair.accessToken(), tokenPair.refreshToken());
    }
}
