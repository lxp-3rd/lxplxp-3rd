package com.ohgiraffers.lxp.course.application.port.command;

public record UpdateCourseCommand(Long courseId, String title, String description, String thumbnailUrl) {
}
