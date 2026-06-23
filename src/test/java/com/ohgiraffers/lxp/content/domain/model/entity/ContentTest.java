package com.ohgiraffers.lxp.content.domain.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTest {

    @Test
    @DisplayName("create: 정상 생성 시 필드값이 설정된다")
    void create_success() {
        Content content = Content.create(1L, 0, "Java 기초", "https://example.com/video");

        assertThat(content.getCourseId()).isEqualTo(1L);
        assertThat(content.getSequence()).isEqualTo(0);
        assertThat(content.getTitle()).isEqualTo("Java 기초");
        assertThat(content.getContentUrl()).isEqualTo("https://example.com/video");
        assertThat(content.getId()).isNull();
    }

    @Test
    @DisplayName("create: courseId가 null이면 예외가 발생한다")
    void create_nullCourseId_throwsException() {
        assertThatThrownBy(() -> Content.create(null, 0, "제목", "https://example.com/video"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강좌 ID는 필수입니다.");
    }

    @Test
    @DisplayName("create: sequence가 음수이면 예외가 발생한다")
    void create_negativeSequence_throwsException() {
        assertThatThrownBy(() -> Content.create(1L, -1, "제목", "https://example.com/video"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("순서는 0 이상이어야 합니다.");
    }

    @Test
    @DisplayName("create: title이 공백이면 예외가 발생한다")
    void create_blankTitle_throwsException() {
        assertThatThrownBy(() -> Content.create(1L, 0, " ", "https://example.com/video"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("콘텐츠 제목은 필수입니다.");
    }

    @Test
    @DisplayName("create: contentUrl이 null이면 예외가 발생한다")
    void create_nullContentUrl_throwsException() {
        assertThatThrownBy(() -> Content.create(1L, 0, "제목", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("콘텐츠 URL은 필수입니다.");
    }

    @Test
    @DisplayName("restore: 저장된 값 그대로 복원된다")
    void restore_success() {
        Content content = Content.restore(10L, 1L, 2, "제목", "https://example.com/video");

        assertThat(content.getId()).isEqualTo(10L);
        assertThat(content.getCourseId()).isEqualTo(1L);
        assertThat(content.getSequence()).isEqualTo(2);
    }
}
