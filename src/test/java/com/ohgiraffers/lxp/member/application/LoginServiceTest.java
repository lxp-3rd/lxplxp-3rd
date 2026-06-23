package com.ohgiraffers.lxp.member.application;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.application.dto.LoginResult;
import com.ohgiraffers.lxp.member.application.port.command.LoginCommand;
import com.ohgiraffers.lxp.member.application.port.out.MemberRepositoryPort;
import com.ohgiraffers.lxp.member.application.port.out.PasswordMatchPort;
import com.ohgiraffers.lxp.member.application.service.LoginService;
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

class LoginServiceTest {

    @Test
    void login_success() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        memberRepository.save(activeMember("learner@lxp.com", "learner", "encoded-password123"));
        LoginService loginService = new LoginService(memberRepository, PasswordMatchPortStub.INSTANCE);

        LoginResult result = loginService.login(new LoginCommand("learner@lxp.com", "password123"));

        assertThat(result.memberId()).isEqualTo(1L);
        assertThat(result.email()).isEqualTo("learner@lxp.com");
        assertThat(result.nickname()).isEqualTo("learner");
        assertThat(result.role()).isEqualTo(MemberRole.LEARNER);
        assertThat(result.status()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void login_fails_when_member_not_found() {
        LoginService loginService = new LoginService(new FakeMemberRepository(), PasswordMatchPortStub.INSTANCE);

        assertThatThrownBy(() -> loginService.login(new LoginCommand("unknown@lxp.com", "password123")))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    void login_fails_when_password_does_not_match() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        memberRepository.save(activeMember("learner@lxp.com", "learner", "encoded-password123"));
        LoginService loginService = new LoginService(memberRepository, PasswordMatchPortStub.INSTANCE);

        assertThatThrownBy(() -> loginService.login(new LoginCommand("learner@lxp.com", "wrong-password")))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_INVALID_PASSWORD);
    }

    @Test
    void login_fails_when_member_is_not_active() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        memberRepository.save(Member.restore(
                null,
                new Email("withdrawn@lxp.com"),
                new Nickname("withdrawn"),
                new EncodedPassword("encoded-password123"),
                MemberRole.LEARNER,
                MemberStatus.WITHDRAWN
        ));
        LoginService loginService = new LoginService(memberRepository, PasswordMatchPortStub.INSTANCE);

        assertThatThrownBy(() -> loginService.login(new LoginCommand("withdrawn@lxp.com", "password123")))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_NOT_ACTIVE);
    }

    private static Member activeMember(String email, String nickname, String encodedPassword) {
        return Member.restore(
                null,
                new Email(email),
                new Nickname(nickname),
                new EncodedPassword(encodedPassword),
                MemberRole.LEARNER,
                MemberStatus.ACTIVE
        );
    }

    private enum PasswordMatchPortStub implements PasswordMatchPort {
        INSTANCE;

        @Override
        public boolean matches(String rawPassword, String encodedPassword) {
            return encodedPassword.equals("encoded-" + rawPassword);
        }
    }

    private static class FakeMemberRepository implements MemberRepositoryPort {

        private final Map<String, Member> members = new HashMap<>();
        private long sequence = 1L;

        @Override
        public boolean existsByEmail(Email email) {
            return members.containsKey(email.value());
        }

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.ofNullable(members.get(email.value()));
        }

        @Override
        public Member save(Member member) {
            Member savedMember = Member.restore(
                    sequence++,
                    member.getEmail(),
                    member.getNickname(),
                    member.getPassword(),
                    member.getRole(),
                    member.getStatus()
            );
            members.put(savedMember.getEmail().value(), savedMember);
            return savedMember;
        }
    }
}
