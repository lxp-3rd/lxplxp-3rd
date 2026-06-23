package com.ohgiraffers.lxp.course.presentation.dto;

import com.ohgiraffers.lxp.course.application.port.command.UnlikeCourseCommand;
import jakarta.validation.constraints.NotNull;

public record UnlikeCourseRequest(
        @NotNull(message = "수강생 ID는 필수입니다.")
        Long learnerId
) {
    public UnlikeCourseCommand toCommand(Long courseId) {
        return new UnlikeCourseCommand(courseId, learnerId);
    }
}
