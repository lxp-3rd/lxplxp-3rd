package com.ohgiraffers.lxp.instructor.application.port.in;

public record ReviewInstructorApplicationCommand(
        Long applicationId,
        ReviewAction action,
        String rejectionReason
) {}
