package com.ohgiraffers.lxp.instructor.application.dto;

import com.ohgiraffers.lxp.instructor.domain.model.entity.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorApplication;

import java.time.LocalDateTime;

public record InstructorApplicationResult(
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
    public static InstructorApplicationResult from(InstructorApplication application) {
        return new InstructorApplicationResult(
                application.getId(),
                application.getMemberId(),
                application.getInstructorName(),
                application.getIntroduction(),
                application.getExpertise(),
                application.getStatus(),
                application.getRejectionReason(),
                application.getCreatedAt(),
                application.getResolvedAt()
        );
    }
}
