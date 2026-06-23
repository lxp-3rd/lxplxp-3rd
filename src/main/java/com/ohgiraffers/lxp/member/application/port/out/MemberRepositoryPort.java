package com.ohgiraffers.lxp.member.application.port.out;

import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;

import java.util.Optional;

public interface MemberRepositoryPort {

    boolean existsByEmail(Email email);

    Optional<Member> findByEmail(Email email);

    Member save(Member member);
}
