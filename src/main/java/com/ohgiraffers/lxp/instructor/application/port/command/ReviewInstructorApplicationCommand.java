package com.ohgiraffers.lxp.instructor.application.port.command;

public record ReviewInstructorApplicationCommand(
        Long applicationId,
        ReviewAction action,
        String rejectionReason
) {}
