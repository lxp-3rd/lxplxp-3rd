package com.ohgiraffers.lxp.instructor.application.port.command;

public record RegisterInstructorProfileCommand(
        Long instructorId,
        String profileImageUrl,
        String bio
) {}
