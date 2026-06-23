package com.ohgiraffers.lxp.enrollment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ohgiraffers.lxp.enrollment.domain.model.entity.Enrollment;
import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EnrollmentTest {

    @Nested
    @DisplayName("enroll 팩토리")
    class Enroll {

        @Test
        @DisplayName("신규 수강은 ACTIVE 상태이며 id는 비어 있다")
        void createsActiveEnrollment() {
            Enrollment enrollment = Enrollment.enroll(1L, 2L);

            assertThat(enrollment.getMemberId()).isEqualTo(1L);
            assertThat(enrollment.getCourseId()).isEqualTo(2L);
            assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.ACTIVE);
            assertThat(enrollment.getId()).isNull();
        }

        @Test
        @DisplayName("memberId가 null이면 예외")
        void rejectsNullMemberId() {
            assertThatThrownBy(() -> Enrollment.enroll(null, 2L))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("courseId가 null이면 예외")
        void rejectsNullCourseId() {
            assertThatThrownBy(() -> Enrollment.enroll(1L, null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("cancel 상태 전이")
    class Cancel {

        @Test
        @DisplayName("ACTIVE 수강을 취소하면 CANCELED로 전이된다")
        void activeToCanceled() {
            Enrollment enrollment = Enrollment.enroll(1L, 2L);

            enrollment.cancel();

            assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.CANCELED);
        }

        @Test
        @DisplayName("이미 CANCELED인 수강을 다시 취소하면 예외")
        void alreadyCanceledThrows() {
            Enrollment enrollment = Enrollment.restore(
                    1L, 1L, 2L, EnrollmentStatus.CANCELED);

            assertThatThrownBy(enrollment::cancel)
                    .isInstanceOf(BusinessException.class)
                    .extracting(e -> ((BusinessException) e).getErrorCode())
                    .isEqualTo(ErrorCode.ENROLLMENT_ALREADY_CANCELED);
        }
    }

    @Nested
    @DisplayName("restore 복원")
    class Restore {

        @Test
        @DisplayName("id/상태까지 그대로 복원한다")
        void restoresAllFields() {
            Enrollment enrollment = Enrollment.restore(
                    10L, 1L, 2L, EnrollmentStatus.ACTIVE);

            assertThat(enrollment.getId()).isEqualTo(10L);
            assertThat(enrollment.getMemberId()).isEqualTo(1L);
            assertThat(enrollment.getCourseId()).isEqualTo(2L);
            assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.ACTIVE);
        }

        @Test
        @DisplayName("status가 null이면 예외")
        void rejectsNullStatus() {
            assertThatThrownBy(() -> Enrollment.restore(
                    10L, 1L, 2L, null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
