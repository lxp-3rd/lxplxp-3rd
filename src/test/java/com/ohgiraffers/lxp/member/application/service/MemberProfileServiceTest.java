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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberProfileServiceTest {

    @Test
    void get_my_profile_success() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        Member savedMember = memberRepository.save(member("learner@lxp.com", "learner"));
        MemberProfileService service = new MemberProfileService(memberRepository);

        MemberProfileResult result = service.getMyProfile(savedMember.getId());

        assertThat(result.memberId()).isEqualTo(savedMember.getId());
        assertThat(result.email()).isEqualTo("learner@lxp.com");
        assertThat(result.nickname()).isEqualTo("learner");
        assertThat(result.role()).isEqualTo(MemberRole.LEARNER);
        assertThat(result.status()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void update_my_profile_success() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        Member savedMember = memberRepository.save(member("learner@lxp.com", "learner"));
        MemberProfileService service = new MemberProfileService(memberRepository);

        MemberProfileResult result = service.updateMyProfile(new UpdateMyProfileCommand(savedMember.getId(), "updated"));

        assertThat(result.memberId()).isEqualTo(savedMember.getId());
        assertThat(result.email()).isEqualTo("learner@lxp.com");
        assertThat(result.nickname()).isEqualTo("updated");
    }

    @Test
    void update_my_profile_fails_when_member_not_found() {
        MemberProfileService service = new MemberProfileService(new FakeMemberRepository());

        assertThatThrownBy(() -> service.updateMyProfile(new UpdateMyProfileCommand(1L, "updated")))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    void update_my_profile_fails_when_nickname_already_exists() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        Member savedMember = memberRepository.save(member("learner@lxp.com", "learner"));
        memberRepository.save(member("other@lxp.com", "other"));
        MemberProfileService service = new MemberProfileService(memberRepository);

        assertThatThrownBy(() -> service.updateMyProfile(new UpdateMyProfileCommand(savedMember.getId(), "other")))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS);
    }

    private Member member(String email, String nickname) {
        return Member.restore(
                null,
                new Email(email),
                new Nickname(nickname),
                new EncodedPassword("encoded-password"),
                MemberRole.LEARNER,
                MemberStatus.ACTIVE
        );
    }

    private static class FakeMemberRepository implements MemberRepositoryPort {

        private final Map<Long, Member> members = new HashMap<>();
        private long sequence = 1L;

        @Override
        public boolean existsByEmail(Email email) {
            return members.values().stream()
                    .anyMatch(member -> member.getEmail().equals(email));
        }

        @Override
        public boolean existsByNickname(Nickname nickname) {
            return members.values().stream()
                    .anyMatch(member -> member.getNickname().equals(nickname));
        }

        @Override
        public Optional<Member> findById(Long memberId) {
            return Optional.ofNullable(members.get(memberId));
        }

        @Override
        public Optional<Member> findByEmail(Email email) {
            return members.values().stream()
                    .filter(member -> member.getEmail().equals(email))
                    .findFirst();
        }

        @Override
        public Member save(Member member) {
            Long memberId = member.getId() == null ? sequence++ : member.getId();
            Member savedMember = Member.restore(
                    memberId,
                    member.getEmail(),
                    member.getNickname(),
                    member.getPassword(),
                    member.getRole(),
                    member.getStatus()
            );
            members.put(savedMember.getId(), savedMember);
            return savedMember;
        }
    }
}
