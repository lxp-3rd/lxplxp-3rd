package com.ohgiraffers.lxp.instructor.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstructorApplicationTest {

    @Test
    @DisplayName("강사 신청 생성 시 상태는 PENDING이다")
    void apply_statusIsPending() {
        InstructorApplication application = InstructorApplication.apply(
                1L,
                "홍길동",
                "10년 경력의 Java 개발자입니다.",
                "백엔드 개발"
        );

        assertThat(application.getStatus()).isEqualTo(ApplicationStatus.PENDING);
    }

    @Test
    @DisplayName("강사 신청 생성 시 신청 일시가 설정된다")
    void apply_createdAtIsSet() {
        InstructorApplication application = InstructorApplication.apply(
                1L,
                "홍길동",
                "10년 경력의 Java 개발자입니다.",
                "백엔드 개발"
        );

        assertThat(application.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("강사 신청 생성 시 회원 ID와 입력 정보가 저장된다")
    void apply_fieldsAreStoredCorrectly() {
        Long memberId = 1L;
        String instructorName = "홍길동";
        String introduction = "10년 경력의 Java 개발자입니다.";
        String expertise = "백엔드 개발";

        InstructorApplication application = InstructorApplication.apply(
                memberId, instructorName, introduction, expertise
        );

        assertThat(application.getMemberId()).isEqualTo(memberId);
        assertThat(application.getInstructorName()).isEqualTo(instructorName);
        assertThat(application.getIntroduction()).isEqualTo(introduction);
        assertThat(application.getExpertise()).isEqualTo(expertise);
    }

    @Test
    @DisplayName("PENDING 신청을 승인하면 상태가 APPROVED가 된다")
    void approve_statusBecomesApproved() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );

        application.approve(LocalDateTime.now());

        assertThat(application.getStatus()).isEqualTo(ApplicationStatus.APPROVED);
        assertThat(application.getResolvedAt()).isNotNull();
    }

    @Test
    @DisplayName("PENDING이 아닌 신청을 승인하면 예외가 발생한다")
    void approve_nonPending_throwsException() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        application.reject("기준 미달", LocalDateTime.now());

        assertThatThrownBy(() -> application.approve(LocalDateTime.now()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("PENDING 신청을 반려하면 상태가 REJECTED가 된다")
    void reject_statusBecomesRejected() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );

        application.reject("기준 미달", LocalDateTime.now());

        assertThat(application.getStatus()).isEqualTo(ApplicationStatus.REJECTED);
        assertThat(application.getRejectionReason()).isEqualTo("기준 미달");
        assertThat(application.getResolvedAt()).isNotNull();
    }

    @Test
    @DisplayName("반려 사유 없이 반려하면 예외가 발생한다")
    void reject_blankReason_throwsException() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );

        assertThatThrownBy(() -> application.reject("", LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("PENDING이 아닌 신청을 반려하면 예외가 발생한다")
    void reject_nonPending_throwsException() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        application.approve(LocalDateTime.now());

        assertThatThrownBy(() -> application.reject("기준 미달", LocalDateTime.now()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("승인 시 처리 시각이 null이면 예외가 발생한다")
    void approve_nullResolvedAt_throwsException() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );

        assertThatThrownBy(() -> application.approve(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("반려 시 처리 시각이 null이면 예외가 발생한다")
    void reject_nullResolvedAt_throwsException() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );

        assertThatThrownBy(() -> application.reject("기준 미달", null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
