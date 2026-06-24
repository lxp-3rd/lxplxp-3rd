package com.ohgiraffers.lxp.enrollment.presentation.dto;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaEntity;
import java.time.LocalDateTime;

public record EnrollmentResponse(
        Long id,
        Long memberId,
        Long courseId,
        String status,
        LocalDateTime createdAt
) {

    public static EnrollmentResponse from(EnrollmentResult result) {
        return new EnrollmentResponse(
                result.id(),
                result.memberId(),
                result.courseId(),
                result.status().name(),
                result.createdAt());
    }

    public static EnrollmentResponse from(EnrollmentJpaEntity entity) {
        return new EnrollmentResponse(
                entity.getId(),
                entity.getMemberId(),
                entity.getCourseId(),
                entity.getStatus().name(),
                entity.getCreatedAt());
    }
    
}
