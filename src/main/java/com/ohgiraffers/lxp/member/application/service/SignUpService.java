package com.ohgiraffers.lxp.member.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.application.dto.SignUpResult;
import com.ohgiraffers.lxp.member.application.port.command.SignUpCommand;
import com.ohgiraffers.lxp.member.application.port.in.SignUpUseCase;
import com.ohgiraffers.lxp.member.application.port.out.MemberRepositoryPort;
import com.ohgiraffers.lxp.member.application.port.out.PasswordEncodePort;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import com.ohgiraffers.lxp.member.domain.model.vo.EncodedPassword;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignUpService implements SignUpUseCase {

    private final MemberRepositoryPort memberRepositoryPort;
    private final PasswordEncodePort passwordEncodePort;

    public SignUpService(MemberRepositoryPort memberRepositoryPort, PasswordEncodePort passwordEncodePort) {
        this.memberRepositoryPort = memberRepositoryPort;
        this.passwordEncodePort = passwordEncodePort;
    }

    @Override
    @Transactional
    public SignUpResult signUp(SignUpCommand command) {
        validatePasswordConfirm(command.password(), command.passwordConfirm());

        Email email = new Email(command.email());
        if (memberRepositoryPort.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.MEMBER_EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncodePort.encode(command.password());
        Member member = Member.signUp(
                email,
                new Nickname(command.nickname()),
                new EncodedPassword(encodedPassword)
        );

        return SignUpResult.from(memberRepositoryPort.save(member));
    }

    private void validatePasswordConfirm(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new BusinessException(ErrorCode.MEMBER_PASSWORD_CONFIRM_NOT_MATCHED);
        }
    }
}
