package com.ohgiraffers.lxp.instructor.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstructorTest {

    @Test
    @DisplayName("강사 생성 시 상태는 ACTIVE이다")
    void create_statusIsActive() {
        Instructor instructor = Instructor.create(1L);

        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.ACTIVE);
    }

    @Test
    @DisplayName("강사 생성 시 회원 ID가 저장된다")
    void create_memberIdIsStored() {
        Instructor instructor = Instructor.create(1L);

        assertThat(instructor.getMemberId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("강사 탈퇴 시 상태가 DELETED가 된다")
    void withdraw_statusBecomesDeleted() {
        Instructor instructor = Instructor.create(1L);

        instructor.withdraw();

        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.DELETED);
    }

    @Test
    @DisplayName("이미 탈퇴한 강사를 다시 탈퇴하면 예외가 발생한다")
    void withdraw_alreadyDeleted_throwsException() {
        Instructor instructor = Instructor.create(1L);
        instructor.withdraw();

        assertThatThrownBy(instructor::withdraw)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("create 시 memberId가 null이면 예외가 발생한다")
    void create_nullMemberId_throwsException() {
        assertThatThrownBy(() -> Instructor.create(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("restore 시 id가 null이면 예외가 발생한다")
    void restore_nullId_throwsException() {
        assertThatThrownBy(() -> Instructor.restore(null, 1L, InstructorStatus.ACTIVE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("restore 시 memberId가 null이면 예외가 발생한다")
    void restore_nullMemberId_throwsException() {
        assertThatThrownBy(() -> Instructor.restore(1L, null, InstructorStatus.ACTIVE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("restore 시 status가 null이면 예외가 발생한다")
    void restore_nullStatus_throwsException() {
        assertThatThrownBy(() -> Instructor.restore(1L, 1L, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
