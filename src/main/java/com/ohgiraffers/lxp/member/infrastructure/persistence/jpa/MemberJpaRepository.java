package com.ohgiraffers.lxp.member.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
