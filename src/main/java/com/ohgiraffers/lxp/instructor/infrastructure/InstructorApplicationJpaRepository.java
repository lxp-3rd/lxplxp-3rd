package com.ohgiraffers.lxp.instructor.infrastructure;

import com.ohgiraffers.lxp.instructor.domain.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorApplicationJpaRepository
        extends JpaRepository<InstructorApplicationJpaEntity, Long> {

    boolean existsByMemberIdAndStatus(Long memberId, ApplicationStatus status);
}
