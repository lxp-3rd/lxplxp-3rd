package com.ohgiraffers.lxp.course.domain.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CourseTest {

    @Test
    @DisplayName("강좌 생성 시 기본 상태는 HIDDEN이다")
    void create_defaultStatusIsHidden() {
        Course course = Course.create(1L, "강좌 제목", "강좌 설명", null);

        assertThat(course.getStatus()).isEqualTo(CourseStatus.HIDDEN);
        assertThat(course.getHiddenBy()).isNull();
    }

    @Test
    @DisplayName("강좌 생성 시 입력값이 정상 저장된다")
    void create_fieldsAreStoredCorrectly() {
        Course course = Course.create(1L, "강좌 제목", "강좌 설명", "https://example.com/thumb.jpg");

        assertThat(course.getInstructorId()).isEqualTo(1L);
        assertThat(course.getTitle()).isEqualTo("강좌 제목");
        assertThat(course.getDescription()).isEqualTo("강좌 설명");
        assertThat(course.getThumbnailUrl()).isEqualTo("https://example.com/thumb.jpg");
    }

    @Test
    @DisplayName("강좌 생성 시 썸네일 URL은 null 허용이다")
    void create_thumbnailUrlCanBeNull() {
        Course course = Course.create(1L, "강좌 제목", "강좌 설명", null);

        assertThat(course.getThumbnailUrl()).isNull();
    }

    @Test
    @DisplayName("강사 ID가 null이면 예외가 발생한다")
    void create_nullInstructorId_throwsException() {
        assertThatThrownBy(() -> Course.create(null, "강좌 제목", "강좌 설명", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강사 ID는 필수입니다.");
    }

    @Test
    @DisplayName("제목이 blank이면 예외가 발생한다")
    void create_blankTitle_throwsException() {
        assertThatThrownBy(() -> Course.create(1L, "  ", "강좌 설명", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강좌 제목은 필수입니다.");
    }

    @Test
    @DisplayName("설명이 blank이면 예외가 발생한다")
    void create_blankDescription_throwsException() {
        assertThatThrownBy(() -> Course.create(1L, "강좌 제목", "", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강좌 설명은 필수입니다.");
    }

    @Test
    @DisplayName("restore 시 ID가 null이면 예외가 발생한다")
    void restore_nullId_throwsException() {
        assertThatThrownBy(() -> Course.restore(null, 1L, "강좌 제목", "강좌 설명", null, CourseStatus.HIDDEN, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강좌 ID는 필수입니다.");
    }

    @Test
    @DisplayName("restore 시 상태가 null이면 예외가 발생한다")
    void restore_nullStatus_throwsException() {
        assertThatThrownBy(() -> Course.restore(1L, 1L, "강좌 제목", "강좌 설명", null, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강좌 상태는 필수입니다.");
    }

    @Test
    @DisplayName("강좌 수정 시 변경된 값이 반영된다")
    void update_fieldsAreUpdated() {
        Course course = Course.create(1L, "원래 제목", "원래 설명", null);

        course.update("새 제목", "새 설명", "https://example.com/new.jpg");

        assertThat(course.getTitle()).isEqualTo("새 제목");
        assertThat(course.getDescription()).isEqualTo("새 설명");
        assertThat(course.getThumbnailUrl()).isEqualTo("https://example.com/new.jpg");
    }

    @Test
    @DisplayName("강좌 수정 시 제목이 blank이면 예외가 발생한다")
    void update_blankTitle_throwsException() {
        Course course = Course.create(1L, "강좌 제목", "강좌 설명", null);

        assertThatThrownBy(() -> course.update("", "새 설명", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강좌 제목은 필수입니다.");
    }

    @Test
    @DisplayName("강좌 수정 시 설명이 blank이면 예외가 발생한다")
    void update_blankDescription_throwsException() {
        Course course = Course.create(1L, "강좌 제목", "강좌 설명", null);

        assertThatThrownBy(() -> course.update("새 제목", "  ", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강좌 설명은 필수입니다.");
    }

    @Test
    @DisplayName("강사가 강좌를 HIDDEN으로 변경하면 hiddenBy가 INSTRUCTOR로 설정된다")
    void changeStatus_instructorHides_setsHiddenByInstructor() {
        Course course = Course.restore(1L, 10L, "강좌 제목", "강좌 설명", null, CourseStatus.PUBLIC, null);

        course.changeStatus(CourseStatus.HIDDEN, HiddenBy.INSTRUCTOR);

        assertThat(course.getStatus()).isEqualTo(CourseStatus.HIDDEN);
        assertThat(course.getHiddenBy()).isEqualTo(HiddenBy.INSTRUCTOR);
    }

    @Test
    @DisplayName("관리자가 강좌를 HIDDEN으로 변경하면 hiddenBy가 ADMIN으로 설정된다")
    void changeStatus_adminHides_setsHiddenByAdmin() {
        Course course = Course.restore(1L, 10L, "강좌 제목", "강좌 설명", null, CourseStatus.PUBLIC, null);

        course.changeStatus(CourseStatus.HIDDEN, HiddenBy.ADMIN);

        assertThat(course.getStatus()).isEqualTo(CourseStatus.HIDDEN);
        assertThat(course.getHiddenBy()).isEqualTo(HiddenBy.ADMIN);
    }

    @Test
    @DisplayName("강사가 INSTRUCTOR로 숨긴 강좌를 PUBLIC으로 변경할 수 있다")
    void changeStatus_instructorUnhidesOwnCourse_success() {
        Course course = Course.restore(1L, 10L, "강좌 제목", "강좌 설명", null, CourseStatus.HIDDEN, HiddenBy.INSTRUCTOR);

        course.changeStatus(CourseStatus.PUBLIC, HiddenBy.INSTRUCTOR);

        assertThat(course.getStatus()).isEqualTo(CourseStatus.PUBLIC);
        assertThat(course.getHiddenBy()).isNull();
    }

    @Test
    @DisplayName("관리자가 숨긴 강좌를 강사가 PUBLIC으로 변경하면 예외가 발생한다")
    void changeStatus_instructorCannotUnhideAdminHidden_throwsException() {
        Course course = Course.restore(1L, 10L, "강좌 제목", "강좌 설명", null, CourseStatus.HIDDEN, HiddenBy.ADMIN);

        assertThatThrownBy(() -> course.changeStatus(CourseStatus.PUBLIC, HiddenBy.INSTRUCTOR))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("관리자가 숨긴 강좌는 강사가 공개할 수 없습니다.");
    }

    @Test
    @DisplayName("관리자는 ADMIN이 숨긴 강좌를 PUBLIC으로 변경할 수 있다")
    void changeStatus_adminCanUnhideAdminHidden_success() {
        Course course = Course.restore(1L, 10L, "강좌 제목", "강좌 설명", null, CourseStatus.HIDDEN, HiddenBy.ADMIN);

        course.changeStatus(CourseStatus.PUBLIC, HiddenBy.ADMIN);

        assertThat(course.getStatus()).isEqualTo(CourseStatus.PUBLIC);
        assertThat(course.getHiddenBy()).isNull();
    }

    @Test
    @DisplayName("강좌를 CLOSED로 변경하면 hiddenBy가 null로 초기화된다")
    void changeStatus_toClosed_clearsHiddenBy() {
        Course course = Course.restore(1L, 10L, "강좌 제목", "강좌 설명", null, CourseStatus.HIDDEN, HiddenBy.INSTRUCTOR);

        course.changeStatus(CourseStatus.CLOSED, HiddenBy.ADMIN);

        assertThat(course.getStatus()).isEqualTo(CourseStatus.CLOSED);
        assertThat(course.getHiddenBy()).isNull();
    }
}
