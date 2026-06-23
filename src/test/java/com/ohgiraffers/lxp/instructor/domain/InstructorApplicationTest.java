package com.ohgiraffers.lxp.instructor.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}
