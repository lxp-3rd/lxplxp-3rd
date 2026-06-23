package com.ohgiraffers.lxp.course.presentation.dto;

import com.ohgiraffers.lxp.course.application.port.command.UpdateCourseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record UpdateCourseRequest(

        @NotBlank(message = "강좌 제목은 필수입니다.")
        @Size(max = 100, message = "강좌 제목은 100자 이하여야 합니다.")
        String title,

        @NotBlank(message = "강좌 설명은 필수입니다.")
        String description,

        @URL(message = "올바른 URL 형식이어야 합니다.")
        String thumbnailUrl
) {
    public UpdateCourseCommand toCommand(Long courseId) {
        return new UpdateCourseCommand(courseId, title, description, thumbnailUrl);
    }
}
