package com.ohgiraffers.lxp.member.application;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.application.dto.SignUpResult;
import com.ohgiraffers.lxp.member.application.port.command.SignUpCommand;
import com.ohgiraffers.lxp.member.application.port.out.MemberRepositoryPort;
import com.ohgiraffers.lxp.member.application.port.out.PasswordEncodePort;
import com.ohgiraffers.lxp.member.application.service.SignUpService;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import com.ohgiraffers.lxp.member.domain.model.vo.EncodedPassword;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SignUpServiceTest {

    @Test
    void 회원가입에_성공한다() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        SignUpService signUpService = new SignUpService(memberRepository, rawPassword -> "encoded-" + rawPassword);

        SignUpResult result = signUpService.signUp(new SignUpCommand(
                "learner@lxp.com",
                "학습자",
                "password123",
                "password123"
        ));

        assertThat(result.memberId()).isEqualTo(1L);
        assertThat(result.email()).isEqualTo("learner@lxp.com");
        assertThat(result.nickname()).isEqualTo("학습자");
        assertThat(result.role()).isEqualTo(MemberRole.LEARNER);
        assertThat(result.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(memberRepository.findSavedPassword("learner@lxp.com")).isEqualTo("encoded-password123");
    }

    @Test
    void 이미_사용중인_이메일이면_예외가_발생한다() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        memberRepository.save(Member.signUp(
                new Email("learner@lxp.com"),
                new Nickname("기존회원"),
                new EncodedPassword("encoded")
        ));
        SignUpService signUpService = new SignUpService(memberRepository, PasswordEncodePortStub.INSTANCE);

        assertThatThrownBy(() -> signUpService.signUp(new SignUpCommand(
                "learner@lxp.com",
                "학습자",
                "password123",
                "password123"
        )))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_EMAIL_ALREADY_EXISTS);
    }

    @Test
    void already_used_nickname_throws_exception() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        memberRepository.save(Member.signUp(
                new Email("existing@lxp.com"),
                new Nickname("learning"),
                new EncodedPassword("encoded")
        ));
        SignUpService signUpService = new SignUpService(memberRepository, PasswordEncodePortStub.INSTANCE);

        assertThatThrownBy(() -> signUpService.signUp(new SignUpCommand(
                "learner@lxp.com",
                "learning",
                "password123",
                "password123"
        )))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS);
    }

    @Test
    void 비밀번호_확인이_일치하지_않으면_예외가_발생한다() {
        SignUpService signUpService = new SignUpService(new FakeMemberRepository(), PasswordEncodePortStub.INSTANCE);

        assertThatThrownBy(() -> signUpService.signUp(new SignUpCommand(
                "learner@lxp.com",
                "학습자",
                "password123",
                "password456"
        )))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_PASSWORD_CONFIRM_NOT_MATCHED);
    }

    private enum PasswordEncodePortStub implements PasswordEncodePort {
        INSTANCE;

        @Override
        public String encode(String rawPassword) {
            return "encoded";
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

        private String findSavedPassword(String email) {
            return members.get(email).getPassword().value();
        }
    }
}
