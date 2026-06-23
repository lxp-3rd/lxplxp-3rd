package com.ohgiraffers.lxp.instructor.presentation;

import com.ohgiraffers.lxp.instructor.application.port.in.RegisterInstructorProfileUseCase.RegisterInstructorProfileCommand;
import jakarta.validation.constraints.NotBlank;

public record RegisterInstructorProfileRequest(

        @NotBlank(message = "프로필 이미지 URL은 필수입니다.")
        String profileImageUrl,

        @NotBlank(message = "자기소개는 필수입니다.")
        String bio
) {
    public RegisterInstructorProfileCommand toCommand(Long instructorId) {
        return new RegisterInstructorProfileCommand(instructorId, profileImageUrl, bio);
    }
}
