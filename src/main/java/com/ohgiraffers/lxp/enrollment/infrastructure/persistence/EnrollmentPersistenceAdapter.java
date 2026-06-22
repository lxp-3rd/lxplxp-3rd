package com.ohgiraffers.lxp.enrollment.infrastructure.persistence;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadEnrollmentPort;
import com.ohgiraffers.lxp.enrollment.application.port.out.SaveEnrollmentPort;
import com.ohgiraffers.lxp.enrollment.domain.model.entity.Enrollment;
import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaEntity;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentPersistenceAdapter implements SaveEnrollmentPort, LoadEnrollmentPort {

    private final EnrollmentJpaRepository repository;

    public EnrollmentPersistenceAdapter(EnrollmentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public EnrollmentResult save(Enrollment enrollment) {
        EnrollmentJpaEntity saved = repository.save(EnrollmentMapper.toJpaEntity(enrollment));
        // 저장 시 BaseEntity 감사가 createdAt을 채운다 → 결과 DTO로 그대로 공급.
        return EnrollmentMapper.toResult(saved);
    }

    @Override
    public boolean existsActiveEnrollment(Long memberId, Long courseId) {
        return repository.existsByMemberIdAndCourseIdAndStatus(memberId, courseId,
                EnrollmentStatus.ACTIVE);
    }
}
