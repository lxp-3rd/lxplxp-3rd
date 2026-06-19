package com.ohgiraffers.lxp.member.application;

import com.ohgiraffers.lxp.global.error.BusinessException;
import com.ohgiraffers.lxp.member.application.dto.SignUpResult;
import com.ohgiraffers.lxp.member.application.port.command.SignUpCommand;
import com.ohgiraffers.lxp.member.application.port.out.MemberRepositoryPort;
import com.ohgiraffers.lxp.member.application.port.out.PasswordEncodePort;
import com.ohgiraffers.lxp.member.application.service.SignUpService;
import com.ohgiraffers.lxp.member.domain.exception.MemberErrorCode;
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
                "student@lxp.com",
                "학습자",
                "password123",
                "password123"
        ));

        assertThat(result.memberId()).isEqualTo(1L);
        assertThat(result.email()).isEqualTo("student@lxp.com");
        assertThat(result.nickname()).isEqualTo("학습자");
        assertThat(result.role()).isEqualTo(MemberRole.STUDENT);
        assertThat(result.status()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(memberRepository.findSavedPassword("student@lxp.com")).isEqualTo("encoded-password123");
    }

    @Test
    void 이미_사용중인_이메일이면_예외가_발생한다() {
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        memberRepository.save(Member.signUp(
                new Email("student@lxp.com"),
                new Nickname("기존회원"),
                new EncodedPassword("encoded")
        ));
        SignUpService signUpService = new SignUpService(memberRepository, PasswordEncodePortStub.INSTANCE);

        assertThatThrownBy(() -> signUpService.signUp(new SignUpCommand(
                "student@lxp.com",
                "학습자",
                "password123",
                "password123"
        )))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(MemberErrorCode.DUPLICATE_EMAIL);
    }

    @Test
    void 비밀번호_확인이_일치하지_않으면_예외가_발생한다() {
        SignUpService signUpService = new SignUpService(new FakeMemberRepository(), PasswordEncodePortStub.INSTANCE);

        assertThatThrownBy(() -> signUpService.signUp(new SignUpCommand(
                "student@lxp.com",
                "학습자",
                "password123",
                "password456"
        )))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(MemberErrorCode.PASSWORD_CONFIRM_NOT_MATCHED);
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
