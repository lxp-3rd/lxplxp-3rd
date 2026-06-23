package com.ohgiraffers.lxp.instructor.presentation.dto;

import com.ohgiraffers.lxp.instructor.application.port.command.UpdateInstructorProfileCommand;
import jakarta.validation.constraints.NotBlank;

public record UpdateInstructorProfileRequest(

        @NotBlank(message = "프로필 이미지 URL은 필수입니다.")
        String profileImageUrl,

        @NotBlank(message = "자기소개는 필수입니다.")
        String bio
) {
    public UpdateInstructorProfileCommand toCommand(Long instructorId) {
        return new UpdateInstructorProfileCommand(instructorId, profileImageUrl, bio);
    }
}
