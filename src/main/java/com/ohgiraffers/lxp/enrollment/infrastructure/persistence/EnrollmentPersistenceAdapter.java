package com.ohgiraffers.lxp.enrollment.infrastructure.persistence;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.application.port.out.LoadEnrollmentPort;
import com.ohgiraffers.lxp.enrollment.application.port.out.SaveEnrollmentPort;
import com.ohgiraffers.lxp.enrollment.application.port.out.UpdateEnrollmentPort;
import com.ohgiraffers.lxp.enrollment.domain.model.entity.Enrollment;
import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaEntity;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentPersistenceAdapter
        implements SaveEnrollmentPort, LoadEnrollmentPort, UpdateEnrollmentPort {

    private final EnrollmentJpaRepository repository;

    public EnrollmentPersistenceAdapter(EnrollmentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public EnrollmentResult save(Enrollment enrollment) {
        EnrollmentJpaEntity saved = repository.save(EnrollmentMapper.toJpaEntity(enrollment));
        return EnrollmentMapper.toResult(saved);
    }

    @Override
    public boolean existsActiveEnrollment(Long memberId, Long courseId) {
        return repository.existsByMemberIdAndCourseIdAndStatus(memberId, courseId, EnrollmentStatus.ACTIVE);
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        return repository.findById(id).map(EnrollmentMapper::toDomain);
    }

    @Override
    public EnrollmentResult update(Enrollment enrollment) {
        EnrollmentJpaEntity entity = repository.findById(enrollment.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENROLLMENT_NOT_FOUND));
        entity.applyStatus(enrollment.getStatus());
        return EnrollmentMapper.toResult(entity);
    }

}
