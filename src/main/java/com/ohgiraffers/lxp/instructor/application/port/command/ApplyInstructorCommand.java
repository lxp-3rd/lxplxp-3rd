package com.ohgiraffers.lxp.instructor.application.port.command;

public record ApplyInstructorCommand(
        Long memberId,
        String instructorName,
        String introduction,
        String expertise
) {}
