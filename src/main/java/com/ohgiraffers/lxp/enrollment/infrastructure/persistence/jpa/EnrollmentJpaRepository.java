package com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, Long> {

    boolean existsByMemberIdAndCourseIdAndStatus(Long memberId, Long courseId, EnrollmentStatus status);

}
