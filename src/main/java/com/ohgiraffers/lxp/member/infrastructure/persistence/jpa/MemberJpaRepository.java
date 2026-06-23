package com.ohgiraffers.lxp.member.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<MemberJpaEntity> findByEmail(String email);

    List<MemberJpaEntity> findAllByDeletedAtIsNullOrderByIdDesc();

    Optional<MemberJpaEntity> findByIdAndDeletedAtIsNull(Long id);
}
