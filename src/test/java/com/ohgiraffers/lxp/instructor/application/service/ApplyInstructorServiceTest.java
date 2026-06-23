package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.command.ApplyInstructorCommand;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorApplicationRepositoryPort;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorRepositoryPort;
import com.ohgiraffers.lxp.instructor.domain.model.entity.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorApplication;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ApplyInstructorServiceTest {

    @InjectMocks
    private ApplyInstructorService applyInstructorService;

    @Mock
    private InstructorApplicationRepositoryPort instructorApplicationRepository;

    @Mock
    private InstructorRepositoryPort instructorRepository;

    @Test
    @DisplayName("강사 신청 성공 시 저장소에 저장된다")
    void apply_success() {
        ApplyInstructorCommand command = new ApplyInstructorCommand(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        given(instructorApplicationRepository.existsByMemberIdAndStatus(
                eq(1L), eq(ApplicationStatus.PENDING))).willReturn(false);
        given(instructorRepository.existsByMemberIdAndStatusIn(
                eq(1L), eq(List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED)))).willReturn(false);

        applyInstructorService.apply(command);

        then(instructorApplicationRepository).should().save(any(InstructorApplication.class));
    }

    @Test
    @DisplayName("이미 PENDING 상태의 신청이 존재하면 예외가 발생한다")
    void apply_duplicatePending_throwsException() {
        ApplyInstructorCommand command = new ApplyInstructorCommand(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        given(instructorApplicationRepository.existsByMemberIdAndStatus(
                eq(1L), eq(ApplicationStatus.PENDING))).willReturn(true);

        assertThatThrownBy(() -> applyInstructorService.apply(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INSTRUCTOR_APPLICATION_ALREADY_EXISTS);

        then(instructorApplicationRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("ACTIVE 강사가 재신청하면 예외가 발생한다")
    void apply_activeInstructor_throwsException() {
        ApplyInstructorCommand command = new ApplyInstructorCommand(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        given(instructorApplicationRepository.existsByMemberIdAndStatus(
                eq(1L), eq(ApplicationStatus.PENDING))).willReturn(false);
        given(instructorRepository.existsByMemberIdAndStatusIn(
                eq(1L), eq(List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED)))).willReturn(true);

        assertThatThrownBy(() -> applyInstructorService.apply(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INSTRUCTOR_ALREADY_EXISTS);

        then(instructorApplicationRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("SUSPENDED 강사가 재신청하면 예외가 발생한다")
    void apply_suspendedInstructor_throwsException() {
        ApplyInstructorCommand command = new ApplyInstructorCommand(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        given(instructorApplicationRepository.existsByMemberIdAndStatus(
                eq(1L), eq(ApplicationStatus.PENDING))).willReturn(false);
        given(instructorRepository.existsByMemberIdAndStatusIn(
                eq(1L), eq(List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED)))).willReturn(true);

        assertThatThrownBy(() -> applyInstructorService.apply(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INSTRUCTOR_ALREADY_EXISTS);

        then(instructorApplicationRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("DELETED 강사는 재신청할 수 있다")
    void apply_deletedInstructor_succeeds() {
        ApplyInstructorCommand command = new ApplyInstructorCommand(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        given(instructorApplicationRepository.existsByMemberIdAndStatus(
                eq(1L), eq(ApplicationStatus.PENDING))).willReturn(false);
        given(instructorRepository.existsByMemberIdAndStatusIn(
                eq(1L), eq(List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED)))).willReturn(false);

        applyInstructorService.apply(command);

        then(instructorApplicationRepository).should().save(any(InstructorApplication.class));
    }
}
