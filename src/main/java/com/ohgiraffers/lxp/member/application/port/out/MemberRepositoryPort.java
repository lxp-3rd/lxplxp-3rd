package com.ohgiraffers.lxp.member.application.port.out;

import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;

import java.util.Optional;

public interface MemberRepositoryPort {

    boolean existsByEmail(Email email);

    boolean existsByNickname(Nickname nickname);

    Optional<Member> findByEmail(Email email);

    Member save(Member member);
}
