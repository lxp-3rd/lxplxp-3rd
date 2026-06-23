package com.ohgiraffers.lxp.instructor.presentation.dto;

import com.ohgiraffers.lxp.instructor.application.port.command.RegisterInstructorProfileCommand;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record RegisterInstructorProfileRequest(

        @NotBlank(message = "프로필 이미지 URL은 필수입니다.")
        @URL(message = "올바른 URL 형식이 아닙니다.")
        String profileImageUrl,

        @NotBlank(message = "자기소개는 필수입니다.")
        String bio
) {
    public RegisterInstructorProfileCommand toCommand(Long instructorId) {
        return new RegisterInstructorProfileCommand(instructorId, profileImageUrl, bio);
    }
}
