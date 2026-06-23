package com.ohgiraffers.lxp.instructor.presentation.dto;

import com.ohgiraffers.lxp.instructor.application.port.command.ApplyInstructorCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ApplyInstructorRequest(

        @NotNull(message = "회원 ID는 필수입니다.")
        @Positive(message = "회원 ID는 양수여야 합니다.")
        Long memberId,

        @NotBlank(message = "강사명은 필수입니다.")
        @Size(min = 2, max = 10, message = "강사명은 2자 이상 10자 이하여야 합니다.")
        String instructorName,

        @NotBlank(message = "자기소개는 필수입니다.")
        @Size(max = 100, message = "자기소개는 100자 이하여야 합니다.")
        String introduction,

        @NotBlank(message = "전문 분야는 필수입니다.")
        @Size(max = 100, message = "전문 분야는 100자 이하여야 합니다.")
        String expertise
) {
    public ApplyInstructorCommand toCommand() {
        return new ApplyInstructorCommand(memberId, instructorName, introduction, expertise);
    }
}
