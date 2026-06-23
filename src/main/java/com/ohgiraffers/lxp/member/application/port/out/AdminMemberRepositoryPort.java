package com.ohgiraffers.lxp.member.application.port.out;

import com.ohgiraffers.lxp.member.application.dto.AdminMemberResult;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;

import java.util.List;
import java.util.Optional;

public interface AdminMemberRepositoryPort {

    List<AdminMemberResult> findAll();

    Optional<AdminMemberResult> findById(Long memberId);

    Optional<AdminMemberResult> changeStatus(Long memberId, MemberStatus status);
}
