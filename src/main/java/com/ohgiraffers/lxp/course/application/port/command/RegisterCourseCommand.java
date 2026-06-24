package com.ohgiraffers.lxp.course.application.port.command;

import java.util.List;

public record RegisterCourseCommand(
        Long instructorId,
        String title,
        String description,
        String thumbnailUrl,
        List<String> contents
) {
}
