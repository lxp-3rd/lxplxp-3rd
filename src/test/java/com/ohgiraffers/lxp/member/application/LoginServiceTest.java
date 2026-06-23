package com.ohgiraffers.lxp.member.application;

import com.ohgiraffers.lxp.auth.application.dto.TokenPair;
import com.ohgiraffers.lxp.auth.application.port.out.RefreshTokenSavePort;
import com.ohgiraffers.lxp.auth.application.port.out.TokenIssuePort;
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

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoginServiceTest {

    private static final Instant REFRESH_TOKEN_EXPIRES_AT = Instant.parse("2026-07-07T00:00:00Z");

    @Test
    void login_success() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        memberRepository.save(activeMember("learner@lxp.com", "learner", "encoded-password123"));
        FakeRefreshTokenSavePort refreshTokenSavePort = new FakeRefreshTokenSavePort();
        LoginService loginService = new LoginService(
                memberRepository,
                PasswordMatchPortStub.INSTANCE,
                TokenIssuePortStub.INSTANCE,
                refreshTokenSavePort
        );

        LoginResult result = loginService.login(new LoginCommand("learner@lxp.com", "password123"));

        assertThat(result.memberId()).isEqualTo(1L);
        assertThat(result.email()).isEqualTo("learner@lxp.com");
        assertThat(result.nickname()).isEqualTo("learner");
        assertThat(result.role()).isEqualTo(MemberRole.LEARNER);
        assertThat(result.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(result.accessToken()).isEqualTo("access-token");
        assertThat(result.refreshToken()).isEqualTo("refresh-token");
        assertThat(refreshTokenSavePort.savedMemberId).isEqualTo(1L);
        assertThat(refreshTokenSavePort.savedRefreshToken).isEqualTo("refresh-token");
        assertThat(refreshTokenSavePort.savedExpiresAt).isEqualTo(REFRESH_TOKEN_EXPIRES_AT);
    }

    @Test
    void login_fails_when_member_not_found() {
        FakeRefreshTokenSavePort refreshTokenSavePort = new FakeRefreshTokenSavePort();
        LoginService loginService = new LoginService(
                new FakeMemberRepository(),
                PasswordMatchPortStub.INSTANCE,
                TokenIssuePortStub.INSTANCE,
                refreshTokenSavePort
        );

        assertThatThrownBy(() -> loginService.login(new LoginCommand("unknown@lxp.com", "password123")))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_NOT_FOUND);
        assertThat(refreshTokenSavePort.savedRefreshToken).isNull();
    }

    @Test
    void login_fails_when_password_does_not_match() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        memberRepository.save(activeMember("learner@lxp.com", "learner", "encoded-password123"));
        FakeRefreshTokenSavePort refreshTokenSavePort = new FakeRefreshTokenSavePort();
        LoginService loginService = new LoginService(
                memberRepository,
                PasswordMatchPortStub.INSTANCE,
                TokenIssuePortStub.INSTANCE,
                refreshTokenSavePort
        );

        assertThatThrownBy(() -> loginService.login(new LoginCommand("learner@lxp.com", "wrong-password")))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_INVALID_PASSWORD);
        assertThat(refreshTokenSavePort.savedRefreshToken).isNull();
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
        FakeRefreshTokenSavePort refreshTokenSavePort = new FakeRefreshTokenSavePort();
        LoginService loginService = new LoginService(
                memberRepository,
                PasswordMatchPortStub.INSTANCE,
                TokenIssuePortStub.INSTANCE,
                refreshTokenSavePort
        );

        assertThatThrownBy(() -> loginService.login(new LoginCommand("withdrawn@lxp.com", "password123")))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_NOT_ACTIVE);
        assertThat(refreshTokenSavePort.savedRefreshToken).isNull();
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

    private enum TokenIssuePortStub implements TokenIssuePort {
        INSTANCE;

        @Override
        public TokenPair issue(Long memberId, MemberRole role) {
            return new TokenPair("access-token", "refresh-token", REFRESH_TOKEN_EXPIRES_AT);
        }
    }

    private static class FakeRefreshTokenSavePort implements RefreshTokenSavePort {

        private Long savedMemberId;
        private String savedRefreshToken;
        private Instant savedExpiresAt;

        @Override
        public void save(Long memberId, String refreshToken, Instant expiresAt) {
            this.savedMemberId = memberId;
            this.savedRefreshToken = refreshToken;
            this.savedExpiresAt = expiresAt;
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
        public boolean existsByNickname(Nickname nickname) {
            return members.values().stream()
                    .anyMatch(member -> member.getNickname().equals(nickname));
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
