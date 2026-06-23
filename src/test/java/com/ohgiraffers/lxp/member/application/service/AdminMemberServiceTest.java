package com.ohgiraffers.lxp.member.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.application.dto.AdminMemberResult;
import com.ohgiraffers.lxp.member.application.port.command.AdminMemberCommand;
import com.ohgiraffers.lxp.member.application.port.command.AdminMemberDetailCommand;
import com.ohgiraffers.lxp.member.application.port.command.ChangeMemberStatusCommand;
import com.ohgiraffers.lxp.member.application.port.out.AdminMemberRepositoryPort;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class AdminMemberServiceTest {

    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2026, 6, 23, 10, 0);
    private static final LocalDateTime UPDATED_AT = LocalDateTime.of(2026, 6, 23, 11, 0);

    @Mock
    private AdminMemberRepositoryPort adminMemberRepositoryPort;

    @InjectMocks
    private AdminMemberService adminMemberService;

    @Test
    void get_members_success() {
        AdminMemberResult member = member(1L, MemberRole.LEARNER, MemberStatus.ACTIVE);
        given(adminMemberRepositoryPort.findAll()).willReturn(List.of(member));

        List<AdminMemberResult> result = adminMemberService.getMembers(adminCommand());

        assertThat(result).containsExactly(member);
    }

    @Test
    void get_members_fails_when_requester_is_not_admin() {
        assertThatThrownBy(() -> adminMemberService.getMembers(new AdminMemberCommand(2L, MemberRole.LEARNER)))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.FORBIDDEN.getMessage());
        then(adminMemberRepositoryPort).should(never()).findAll();
    }

    @Test
    void get_member_success() {
        AdminMemberResult member = member(1L, MemberRole.LEARNER, MemberStatus.ACTIVE);
        given(adminMemberRepositoryPort.findById(1L)).willReturn(Optional.of(member));

        AdminMemberResult result = adminMemberService.getMember(new AdminMemberDetailCommand(99L, MemberRole.ADMIN, 1L));

        assertThat(result).isEqualTo(member);
    }

    @Test
    void get_member_fails_when_member_not_found() {
        given(adminMemberRepositoryPort.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> adminMemberService.getMember(new AdminMemberDetailCommand(99L, MemberRole.ADMIN, 1L)))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    void change_status_success() {
        AdminMemberResult changedMember = member(1L, MemberRole.LEARNER, MemberStatus.SUSPENDED);
        given(adminMemberRepositoryPort.changeStatus(1L, MemberStatus.SUSPENDED)).willReturn(Optional.of(changedMember));

        AdminMemberResult result = adminMemberService.changeStatus(
                new ChangeMemberStatusCommand(99L, MemberRole.ADMIN, 1L, MemberStatus.SUSPENDED)
        );

        assertThat(result.status()).isEqualTo(MemberStatus.SUSPENDED);
    }

    @Test
    void change_status_fails_when_requester_is_not_admin() {
        assertThatThrownBy(() -> adminMemberService.changeStatus(
                new ChangeMemberStatusCommand(2L, MemberRole.LEARNER, 1L, MemberStatus.SUSPENDED)
        ))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.FORBIDDEN.getMessage());
        then(adminMemberRepositoryPort).should(never()).changeStatus(1L, MemberStatus.SUSPENDED);
    }

    private AdminMemberCommand adminCommand() {
        return new AdminMemberCommand(99L, MemberRole.ADMIN);
    }

    private AdminMemberResult member(Long memberId, MemberRole role, MemberStatus status) {
        return new AdminMemberResult(
                memberId,
                "member" + memberId + "@lxp.com",
                "member" + memberId,
                role,
                status,
                CREATED_AT,
                UPDATED_AT
        );
    }
}
