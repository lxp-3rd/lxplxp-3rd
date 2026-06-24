package com.ohgiraffers.lxp.course.domain.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CourseLikeTest {

    @Test
    @DisplayName("create: 정상 생성 시 courseId와 learnerId가 설정된다")
    void create_success() {
        CourseLike like = CourseLike.create(1L, 100L);

        assertThat(like.getCourseId()).isEqualTo(1L);
        assertThat(like.getLearnerId()).isEqualTo(100L);
        assertThat(like.getId()).isNull();
        assertThat(like.getCreatedAt()).isNull();
    }

    @Test
    @DisplayName("create: courseId가 null이면 예외가 발생한다")
    void create_nullCourseId_throwsException() {
        assertThatThrownBy(() -> CourseLike.create(null, 100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강좌 ID는 필수입니다.");
    }

    @Test
    @DisplayName("create: learnerId가 null이면 예외가 발생한다")
    void create_nullLearnerId_throwsException() {
        assertThatThrownBy(() -> CourseLike.create(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수강생 ID는 필수입니다.");
    }

    @Test
    @DisplayName("restore: 저장된 값 그대로 복원된다")
    void restore_success() {
        LocalDateTime now = LocalDateTime.now();
        CourseLike like = CourseLike.restore(10L, 1L, 100L, now);

        assertThat(like.getId()).isEqualTo(10L);
        assertThat(like.getCourseId()).isEqualTo(1L);
        assertThat(like.getLearnerId()).isEqualTo(100L);
        assertThat(like.getCreatedAt()).isEqualTo(now);
    }
}
