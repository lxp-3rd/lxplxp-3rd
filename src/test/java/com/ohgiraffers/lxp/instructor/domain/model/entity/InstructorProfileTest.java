package com.ohgiraffers.lxp.instructor.domain.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstructorProfileTest {

    @Test
    @DisplayName("강사 ID가 null이면 예외가 발생한다")
    void create_nullInstructorId_throwsException() {
        assertThatThrownBy(() -> InstructorProfile.create(null, "https://example.com/image.jpg", "자기소개"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강사 ID는 필수입니다.");
    }

    @Test
    @DisplayName("restore 시 id가 null이면 예외가 발생한다")
    void restore_nullId_throwsException() {
        assertThatThrownBy(() -> InstructorProfile.restore(null, 1L, "https://example.com/image.jpg", "자기소개"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("프로필 ID는 필수입니다.");
    }

    @Test
    @DisplayName("restore 시 instructorId가 null이면 예외가 발생한다")
    void restore_nullInstructorId_throwsException() {
        assertThatThrownBy(() -> InstructorProfile.restore(1L, null, "https://example.com/image.jpg", "자기소개"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강사 ID는 필수입니다.");
    }

    @Test
    @DisplayName("restore 시 profileImageUrl이 blank이면 예외가 발생한다")
    void restore_blankProfileImageUrl_throwsException() {
        assertThatThrownBy(() -> InstructorProfile.restore(1L, 1L, "", "자기소개"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("프로필 이미지 URL은 필수입니다.");
    }

    @Test
    @DisplayName("restore 시 bio가 blank이면 예외가 발생한다")
    void restore_blankBio_throwsException() {
        assertThatThrownBy(() -> InstructorProfile.restore(1L, 1L, "https://example.com/image.jpg", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자기소개는 필수입니다.");
    }

    @Test
    @DisplayName("프로필 생성 시 입력값이 정상 저장된다")
    void create_fieldsAreStoredCorrectly() {
        InstructorProfile profile = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "10년 경력의 Java 개발자입니다."
        );

        assertThat(profile.getInstructorId()).isEqualTo(1L);
        assertThat(profile.getProfileImageUrl()).isEqualTo("https://example.com/image.jpg");
        assertThat(profile.getBio()).isEqualTo("10년 경력의 Java 개발자입니다.");
    }

    @Test
    @DisplayName("프로필 이미지 URL이 빈 값이면 예외가 발생한다")
    void create_blankProfileImageUrl_throwsException() {
        assertThatThrownBy(() -> InstructorProfile.create(1L, "", "자기소개"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("프로필 이미지 URL은 필수입니다.");
    }

    @Test
    @DisplayName("자기소개가 빈 값이면 예외가 발생한다")
    void create_blankBio_throwsException() {
        assertThatThrownBy(() -> InstructorProfile.create(1L, "https://example.com/image.jpg", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자기소개는 필수입니다.");
    }

    @Test
    @DisplayName("프로필 수정 시 변경된 값이 반영된다")
    void update_fieldsAreUpdated() {
        InstructorProfile profile = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "기존 자기소개"
        );

        profile.update("https://example.com/new.jpg", "새로운 자기소개");

        assertThat(profile.getProfileImageUrl()).isEqualTo("https://example.com/new.jpg");
        assertThat(profile.getBio()).isEqualTo("새로운 자기소개");
    }

    @Test
    @DisplayName("프로필 수정 시 이미지 URL이 빈 값이면 예외가 발생한다")
    void update_blankProfileImageUrl_throwsException() {
        InstructorProfile profile = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "자기소개"
        );

        assertThatThrownBy(() -> profile.update("", "새로운 자기소개"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("프로필 이미지 URL은 필수입니다.");
    }

    @Test
    @DisplayName("프로필 수정 시 자기소개가 빈 값이면 예외가 발생한다")
    void update_blankBio_throwsException() {
        InstructorProfile profile = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "자기소개"
        );

        assertThatThrownBy(() -> profile.update("https://example.com/new.jpg", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자기소개는 필수입니다.");
    }
}
