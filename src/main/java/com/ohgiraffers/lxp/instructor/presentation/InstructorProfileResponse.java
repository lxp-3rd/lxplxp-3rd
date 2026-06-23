package com.ohgiraffers.lxp.instructor.presentation;

import com.ohgiraffers.lxp.instructor.domain.InstructorProfile;

public record InstructorProfileResponse(
        Long instructorId,
        String profileImageUrl,
        String bio
) {
    public static InstructorProfileResponse from(InstructorProfile profile) {
        return new InstructorProfileResponse(
                profile.getInstructorId(),
                profile.getProfileImageUrl(),
                profile.getBio()
        );
    }
}
