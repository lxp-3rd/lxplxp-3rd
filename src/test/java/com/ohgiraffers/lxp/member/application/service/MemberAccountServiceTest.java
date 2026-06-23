package com.ohgiraffers.lxp.member.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.application.port.command.ChangePasswordCommand;
import com.ohgiraffers.lxp.member.application.port.command.WithdrawMemberCommand;
import com.ohgiraffers.lxp.member.application.port.out.MemberRepositoryPort;
import com.ohgiraffers.lxp.member.application.port.out.PasswordEncodePort;
import com.ohgiraffers.lxp.member.application.port.out.PasswordMatchPort;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import com.ohgiraffers.lxp.member.domain.model.vo.EncodedPassword;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class MemberAccountServiceTest {

    @Mock
    private MemberRepositoryPort memberRepositoryPort;

    @Mock
    private PasswordMatchPort passwordMatchPort;

    @Mock
    private PasswordEncodePort passwordEncodePort;

    @InjectMocks
    private MemberAccountService memberAccountService;

    @Test
    void change_password_success() {
        Member member = activeMember();
        given(memberRepositoryPort.findById(1L)).willReturn(Optional.of(member));
        given(passwordMatchPort.matches("current-password", "encoded-current-password")).willReturn(true);
        given(passwordEncodePort.encode("new-password123")).willReturn("encoded-new-password123");

        memberAccountService.changePassword(new ChangePasswordCommand(
                1L,
                "current-password",
                "new-password123",
                "new-password123"
        ));

        ArgumentCaptor<Member> captor = forClass(Member.class);
        then(memberRepositoryPort).should().save(captor.capture());
        assertThat(captor.getValue().getPassword().value()).isEqualTo("encoded-new-password123");
        assertThat(captor.getValue().getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void change_password_fails_when_current_password_does_not_match() {
        Member member = activeMember();
        given(memberRepositoryPort.findById(1L)).willReturn(Optional.of(member));
        given(passwordMatchPort.matches("wrong-password", "encoded-current-password")).willReturn(false);

        assertThatThrownBy(() -> memberAccountService.changePassword(new ChangePasswordCommand(
                1L,
                "wrong-password",
                "new-password123",
                "new-password123"
        )))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.MEMBER_INVALID_PASSWORD.getMessage());
        then(memberRepositoryPort).should(never()).save(member);
    }

    @Test
    void change_password_fails_when_password_confirm_does_not_match() {
        assertThatThrownBy(() -> memberAccountService.changePassword(new ChangePasswordCommand(
                1L,
                "current-password",
                "new-password123",
                "other-password123"
        )))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.MEMBER_PASSWORD_CONFIRM_NOT_MATCHED.getMessage());
        then(memberRepositoryPort).should(never()).findById(1L);
    }

    @Test
    void withdraw_success() {
        Member member = activeMember();
        given(memberRepositoryPort.findById(1L)).willReturn(Optional.of(member));
        given(passwordMatchPort.matches("current-password", "encoded-current-password")).willReturn(true);

        memberAccountService.withdraw(new WithdrawMemberCommand(1L, "current-password"));

        ArgumentCaptor<Member> captor = forClass(Member.class);
        then(memberRepositoryPort).should().save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(MemberStatus.WITHDRAWN);
    }

    @Test
    void withdraw_fails_when_member_is_not_active() {
        Member member = Member.restore(
                1L,
                new Email("member@lxp.com"),
                new Nickname("member"),
                new EncodedPassword("encoded-current-password"),
                MemberRole.LEARNER,
                MemberStatus.WITHDRAWN
        );
        given(memberRepositoryPort.findById(1L)).willReturn(Optional.of(member));

        assertThatThrownBy(() -> memberAccountService.withdraw(new WithdrawMemberCommand(1L, "current-password")))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_ACTIVE.getMessage());
        then(memberRepositoryPort).should(never()).save(member);
    }

    private Member activeMember() {
        return Member.restore(
                1L,
                new Email("member@lxp.com"),
                new Nickname("member"),
                new EncodedPassword("encoded-current-password"),
                MemberRole.LEARNER,
                MemberStatus.ACTIVE
        );
    }
}
