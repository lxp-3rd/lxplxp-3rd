package com.ohgiraffers.lxp.course.application.port.command;

public record RegisterCourseCommand(
        Long instructorId,
        String title,
        String description,
        String thumbnailUrl
) {
}
