package com.ohgiraffers.lxp.instructor.application.port.command;

public record UpdateInstructorProfileCommand(
        Long instructorId,
        String profileImageUrl,
        String bio
) {}
