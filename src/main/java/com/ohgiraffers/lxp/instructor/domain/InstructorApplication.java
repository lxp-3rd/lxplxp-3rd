package com.ohgiraffers.lxp.instructor.domain;

import java.time.LocalDateTime;

public class InstructorApplication {

    private Long id;
    private Long memberId;
    private String instructorName;
    private String introduction;
    private String expertise;
    private ApplicationStatus status;
    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    private InstructorApplication() {
    }

    public static InstructorApplication apply(
            Long memberId,
            String instructorName,
            String introduction,
            String expertise
    ) {
        InstructorApplication application = new InstructorApplication();
        application.memberId = memberId;
        application.instructorName = instructorName;
        application.introduction = introduction;
        application.expertise = expertise;
        application.status = ApplicationStatus.PENDING;
        application.createdAt = LocalDateTime.now();
        return application;
    }

    public static InstructorApplication reconstitute(
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
        InstructorApplication application = new InstructorApplication();
        application.id = id;
        application.memberId = memberId;
        application.instructorName = instructorName;
        application.introduction = introduction;
        application.expertise = expertise;
        application.status = status;
        application.rejectionReason = rejectionReason;
        application.createdAt = createdAt;
        application.resolvedAt = resolvedAt;
        return application;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getExpertise() {
        return expertise;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }
}
