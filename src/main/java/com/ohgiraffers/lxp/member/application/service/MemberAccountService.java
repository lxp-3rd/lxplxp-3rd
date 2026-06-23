package com.ohgiraffers.lxp.member.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.application.port.command.ChangePasswordCommand;
import com.ohgiraffers.lxp.member.application.port.command.WithdrawMemberCommand;
import com.ohgiraffers.lxp.member.application.port.in.ChangePasswordUseCase;
import com.ohgiraffers.lxp.member.application.port.in.WithdrawMemberUseCase;
import com.ohgiraffers.lxp.member.application.port.out.MemberRepositoryPort;
import com.ohgiraffers.lxp.member.application.port.out.PasswordEncodePort;
import com.ohgiraffers.lxp.member.application.port.out.PasswordMatchPort;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;
import com.ohgiraffers.lxp.member.domain.model.vo.EncodedPassword;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberAccountService implements ChangePasswordUseCase, WithdrawMemberUseCase {

    private final MemberRepositoryPort memberRepositoryPort;
    private final PasswordMatchPort passwordMatchPort;
    private final PasswordEncodePort passwordEncodePort;

    public MemberAccountService(
            MemberRepositoryPort memberRepositoryPort,
            PasswordMatchPort passwordMatchPort,
            PasswordEncodePort passwordEncodePort
    ) {
        this.memberRepositoryPort = memberRepositoryPort;
        this.passwordMatchPort = passwordMatchPort;
        this.passwordEncodePort = passwordEncodePort;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordCommand command) {
        validatePasswordConfirm(command.newPassword(), command.newPasswordConfirm());
        Member member = getActiveMember(command.memberId());
        validatePassword(command.currentPassword(), member);

        String encodedPassword = passwordEncodePort.encode(command.newPassword());
        memberRepositoryPort.save(member.changePassword(new EncodedPassword(encodedPassword)));
    }

    @Override
    @Transactional
    public void withdraw(WithdrawMemberCommand command) {
        Member member = getActiveMember(command.memberId());
        validatePassword(command.password(), member);

        memberRepositoryPort.save(member.withdraw());
    }

    private Member getActiveMember(Long memberId) {
        Member member = memberRepositoryPort.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_ACTIVE);
        }
        return member;
    }

    private void validatePassword(String rawPassword, Member member) {
        if (!passwordMatchPort.matches(rawPassword, member.getPassword().value())) {
            throw new BusinessException(ErrorCode.MEMBER_INVALID_PASSWORD);
        }
    }

    private void validatePasswordConfirm(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new BusinessException(ErrorCode.MEMBER_PASSWORD_CONFIRM_NOT_MATCHED);
        }
    }
}
