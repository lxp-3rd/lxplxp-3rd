package com.ohgiraffers.lxp.member.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import com.ohgiraffers.lxp.member.domain.model.vo.EncodedPassword;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@Import(MemberPersistenceAdapter.class)
class MemberPersistenceAdapterTest {

    @Autowired
    private MemberPersistenceAdapter memberPersistenceAdapter;

    @Test
    void save_duplicate_nickname_throws_business_exception() {
        memberPersistenceAdapter.save(member("first@lxp.com", "learning"));

        assertThatThrownBy(() -> memberPersistenceAdapter.save(member("second@lxp.com", "learning")))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS);
    }

    private Member member(String email, String nickname) {
        return Member.signUp(
                new Email(email),
                new Nickname(nickname),
                new EncodedPassword("encoded-password")
        );
    }
}
