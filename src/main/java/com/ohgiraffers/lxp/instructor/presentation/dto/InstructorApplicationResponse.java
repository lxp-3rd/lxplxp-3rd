package com.ohgiraffers.lxp.instructor.presentation.dto;

import com.ohgiraffers.lxp.instructor.application.dto.InstructorApplicationResult;
import com.ohgiraffers.lxp.instructor.domain.model.entity.ApplicationStatus;

import java.time.LocalDateTime;

public record InstructorApplicationResponse(
        Long id,
        Long memberId,
        String instructorName,
        String introduction,
        String expertise,
        ApplicationStatus status,
        String rejectionReason,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt
) {
    public static InstructorApplicationResponse from(InstructorApplicationResult result) {
        return new InstructorApplicationResponse(
                result.id(),
                result.memberId(),
                result.instructorName(),
                result.introduction(),
                result.expertise(),
                result.status(),
                result.rejectionReason(),
                result.createdAt(),
                result.resolvedAt()
        );
    }
}
