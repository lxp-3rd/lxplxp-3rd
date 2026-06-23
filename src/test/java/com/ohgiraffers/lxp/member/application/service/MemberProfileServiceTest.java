package com.ohgiraffers.lxp.member.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.application.dto.MemberProfileResult;
import com.ohgiraffers.lxp.member.application.port.command.UpdateMyProfileCommand;
import com.ohgiraffers.lxp.member.application.port.out.MemberRepositoryPort;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import com.ohgiraffers.lxp.member.domain.model.vo.EncodedPassword;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class MemberProfileServiceTest {

    @Mock
    private MemberRepositoryPort memberRepositoryPort;

    @InjectMocks
    private MemberProfileService service;

    @Test
    void get_my_profile_success() {
        Member savedMember = member(1L, "learner@lxp.com", "learner");
        given(memberRepositoryPort.findById(1L)).willReturn(Optional.of(savedMember));

        MemberProfileResult result = service.getMyProfile(1L);

        assertThat(result.memberId()).isEqualTo(1L);
        assertThat(result.email()).isEqualTo("learner@lxp.com");
        assertThat(result.nickname()).isEqualTo("learner");
        assertThat(result.role()).isEqualTo(MemberRole.LEARNER);
        assertThat(result.status()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void get_my_profile_fails_when_member_not_found() {
        given(memberRepositoryPort.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.getMyProfile(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    void update_my_profile_success() {
        Member savedMember = member(1L, "learner@lxp.com", "learner");
        Member updatedMember = member(1L, "learner@lxp.com", "updated");
        given(memberRepositoryPort.findById(1L)).willReturn(Optional.of(savedMember));
        given(memberRepositoryPort.existsByNickname(new Nickname("updated"))).willReturn(false);
        given(memberRepositoryPort.save(any(Member.class))).willReturn(updatedMember);

        MemberProfileResult result = service.updateMyProfile(new UpdateMyProfileCommand(1L, "updated"));

        assertThat(result.memberId()).isEqualTo(1L);
        assertThat(result.email()).isEqualTo("learner@lxp.com");
        assertThat(result.nickname()).isEqualTo("updated");
    }

    @Test
    void update_my_profile_fails_when_member_not_found() {
        given(memberRepositoryPort.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateMyProfile(new UpdateMyProfileCommand(1L, "updated")))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    void update_my_profile_fails_when_nickname_already_exists() {
        Member savedMember = member(1L, "learner@lxp.com", "learner");
        given(memberRepositoryPort.findById(1L)).willReturn(Optional.of(savedMember));
        given(memberRepositoryPort.existsByNickname(new Nickname("other"))).willReturn(true);

        assertThatThrownBy(() -> service.updateMyProfile(new UpdateMyProfileCommand(1L, "other")))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS.getMessage());
        then(memberRepositoryPort).should(never()).save(any(Member.class));
    }

    private Member member(Long id, String email, String nickname) {
        return Member.restore(
                id,
                new Email(email),
                new Nickname(nickname),
                new EncodedPassword("encoded-password"),
                MemberRole.LEARNER,
                MemberStatus.ACTIVE
        );
    }
}
