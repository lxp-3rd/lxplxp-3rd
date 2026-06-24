package com.ohgiraffers.lxp.course.presentation.dto;

import com.ohgiraffers.lxp.course.application.port.command.RegisterCourseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

public record RegisterCourseRequest(

        @NotNull(message = "강사 ID는 필수입니다.")
        @Positive(message = "강사 ID는 양수여야 합니다.")
        Long instructorId,

        @NotBlank(message = "강좌 제목은 필수입니다.")
        @Size(max = 100, message = "강좌 제목은 100자 이하여야 합니다.")
        String title,

        @NotBlank(message = "강좌 설명은 필수입니다.")
        String description,

        String thumbnailUrl,

        List<@NotBlank(message = "콘텐츠 제목은 필수입니다.") @Size(max = 100, message = "콘텐츠 제목은 100자 이하여야 합니다.") String> contents
) {
    public RegisterCourseCommand toCommand() {
        return new RegisterCourseCommand(instructorId, title, description, thumbnailUrl, contents);
    }
}
