package com.ohgiraffers.lxp.enrollment.infrastructure.persistence;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.domain.model.entity.Enrollment;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaEntity;

final class EnrollmentMapper {

    private EnrollmentMapper() {
    }

    static EnrollmentJpaEntity toJpaEntity(Enrollment enrollment) {
        return new EnrollmentJpaEntity(
                enrollment.getMemberId(),
                enrollment.getCourseId(),
                enrollment.getStatus());
    }

    static EnrollmentResult toResult(EnrollmentJpaEntity entity) {
        return new EnrollmentResult(
                entity.getId(),
                entity.getMemberId(),
                entity.getCourseId(),
                entity.getStatus(),
                entity.getCreatedAt());
    }

    static Enrollment toDomain(EnrollmentJpaEntity entity) {
        return Enrollment.restore(
                entity.getId(),
                entity.getMemberId(),
                entity.getCourseId(),
                entity.getStatus());
    }
}
