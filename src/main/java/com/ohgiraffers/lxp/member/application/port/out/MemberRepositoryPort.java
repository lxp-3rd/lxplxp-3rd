package com.ohgiraffers.lxp.member.application.port.out;

import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;

public interface MemberRepositoryPort {

    boolean existsByEmail(Email email);

    Member save(Member member);
}
