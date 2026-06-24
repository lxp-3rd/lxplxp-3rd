package com.ohgiraffers.lxp.member.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

    List<MemberJpaEntity> findByIdInAndRoleAndDeletedAtIsNull(List<Long> ids, MemberRole role);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<MemberJpaEntity> findByIdAndDeletedAtIsNull(Long id);

    Optional<MemberJpaEntity> findByEmail(String email);

    List<MemberJpaEntity> findAllByDeletedAtIsNullOrderByIdDesc();
}