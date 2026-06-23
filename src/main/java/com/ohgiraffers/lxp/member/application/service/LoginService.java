package com.ohgiraffers.lxp.member.application.service;

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

    public LoginService(MemberRepositoryPort memberRepositoryPort, PasswordMatchPort passwordMatchPort) {
        this.memberRepositoryPort = memberRepositoryPort;
        this.passwordMatchPort = passwordMatchPort;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResult login(LoginCommand command) {
        Member member = memberRepositoryPort.findByEmail(new Email(command.email()))
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordMatchPort.matches(command.password(), member.getPassword().value())) {
            throw new BusinessException(ErrorCode.MEMBER_INVALID_PASSWORD);
        }

        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_ACTIVE);
        }

        return LoginResult.from(member);
    }
}
