package com.ohgiraffers.lxp.instructor.domain;

public class InstructorProfile {

    private Long id;
    private Long instructorId;
    private String profileImageUrl;
    private String bio;

    private InstructorProfile() {
    }

    public static InstructorProfile create(Long instructorId, String profileImageUrl, String bio) {
        if (instructorId == null) {
            throw new IllegalArgumentException("강사 ID는 필수입니다.");
        }
        if (profileImageUrl == null || profileImageUrl.isBlank()) {
            throw new IllegalArgumentException("프로필 이미지 URL은 필수입니다.");
        }
        if (bio == null || bio.isBlank()) {
            throw new IllegalArgumentException("자기소개는 필수입니다.");
        }
        InstructorProfile profile = new InstructorProfile();
        profile.instructorId = instructorId;
        profile.profileImageUrl = profileImageUrl;
        profile.bio = bio;
        return profile;
    }

    public static InstructorProfile restore(
            Long id,
            Long instructorId,
            String profileImageUrl,
            String bio
    ) {
        if (id == null) throw new IllegalArgumentException("프로필 ID는 필수입니다.");
        if (instructorId == null) throw new IllegalArgumentException("강사 ID는 필수입니다.");
        if (profileImageUrl == null || profileImageUrl.isBlank()) throw new IllegalArgumentException("프로필 이미지 URL은 필수입니다.");
        if (bio == null || bio.isBlank()) throw new IllegalArgumentException("자기소개는 필수입니다.");
        InstructorProfile profile = new InstructorProfile();
        profile.id = id;
        profile.instructorId = instructorId;
        profile.profileImageUrl = profileImageUrl;
        profile.bio = bio;
        return profile;
    }

    public void update(String profileImageUrl, String bio) {
        if (profileImageUrl == null || profileImageUrl.isBlank()) {
            throw new IllegalArgumentException("프로필 이미지 URL은 필수입니다.");
        }
        if (bio == null || bio.isBlank()) {
            throw new IllegalArgumentException("자기소개는 필수입니다.");
        }
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getBio() {
        return bio;
    }
}
