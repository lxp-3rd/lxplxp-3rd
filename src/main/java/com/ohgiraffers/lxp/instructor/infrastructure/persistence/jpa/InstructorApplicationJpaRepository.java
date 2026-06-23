package com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.instructor.domain.model.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorApplicationJpaRepository
        extends JpaRepository<InstructorApplicationJpaEntity, Long> {

    boolean existsByMemberIdAndStatus(Long memberId, ApplicationStatus status);
}
