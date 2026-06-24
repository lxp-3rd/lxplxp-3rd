package com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstructorJpaRepository extends JpaRepository<InstructorJpaEntity, Long> {

    boolean existsByIdAndStatusInAndDeletedAtIsNull(Long id, List<InstructorStatus> statuses);

    boolean existsByMemberIdAndStatusIn(Long memberId, List<InstructorStatus> statuses);

    Optional<InstructorJpaEntity> findByMemberId(Long memberId);
}
