package com.ohgiraffers.lxp.member.application.port.out;

import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;

public interface MemberRepositoryPort {

    boolean existsByEmail(Email email);

    boolean existsByNickname(Nickname nickname);

    Member save(Member member);
}
