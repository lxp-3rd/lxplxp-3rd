package com.ohgiraffers.lxp.course.presentation.dto;

import com.ohgiraffers.lxp.course.application.port.command.LikeCourseCommand;
import jakarta.validation.constraints.NotNull;

public record LikeCourseRequest(
        @NotNull(message = "수강생 ID는 필수입니다.")
        Long learnerId
) {
    public LikeCourseCommand toCommand(Long courseId) {
        return new LikeCourseCommand(courseId, learnerId);
    }
}
