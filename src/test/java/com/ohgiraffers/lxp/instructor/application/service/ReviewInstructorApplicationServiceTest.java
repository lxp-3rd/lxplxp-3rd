package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.command.ReviewAction;
import com.ohgiraffers.lxp.instructor.application.port.command.ReviewInstructorApplicationCommand;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorApplicationRepositoryPort;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorRepositoryPort;
import com.ohgiraffers.lxp.instructor.domain.model.entity.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.model.entity.Instructor;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorApplication;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ReviewInstructorApplicationServiceTest {

    @InjectMocks
    private ReviewInstructorApplicationService reviewInstructorApplicationService;

    @Mock
    private InstructorApplicationRepositoryPort instructorApplicationRepository;

    @Mock
    private InstructorRepositoryPort instructorRepository;

    @Test
    @DisplayName("승인 시 신청 상태가 APPROVED로 변경되고 Instructor가 생성된다")
    void review_approve_savesApprovedApplicationAndCreatesInstructor() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        given(instructorApplicationRepository.findById(1L)).willReturn(Optional.of(application));
        given(instructorApplicationRepository.save(any())).willReturn(application);

        ReviewInstructorApplicationCommand command =
                new ReviewInstructorApplicationCommand(1L, ReviewAction.APPROVE, null);

        reviewInstructorApplicationService.review(command);

        ArgumentCaptor<InstructorApplication> applicationCaptor =
                ArgumentCaptor.forClass(InstructorApplication.class);
        then(instructorApplicationRepository).should().save(applicationCaptor.capture());
        assertThat(applicationCaptor.getValue().getStatus()).isEqualTo(ApplicationStatus.APPROVED);

        ArgumentCaptor<Instructor> instructorCaptor =
                ArgumentCaptor.forClass(Instructor.class);
        then(instructorRepository).should().save(instructorCaptor.capture());
        assertThat(instructorCaptor.getValue().getMemberId()).isEqualTo(1L);
        assertThat(instructorCaptor.getValue().getStatus()).isEqualTo(InstructorStatus.ACTIVE);
    }

    @Test
    @DisplayName("반려 시 신청 상태가 REJECTED로 변경되고 Instructor는 생성되지 않는다")
    void review_reject_savesRejectedApplicationAndNoInstructor() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        given(instructorApplicationRepository.findById(1L)).willReturn(Optional.of(application));
        given(instructorApplicationRepository.save(any())).willReturn(application);

        ReviewInstructorApplicationCommand command =
                new ReviewInstructorApplicationCommand(1L, ReviewAction.REJECT, "기준 미달");

        reviewInstructorApplicationService.review(command);

        ArgumentCaptor<InstructorApplication> applicationCaptor =
                ArgumentCaptor.forClass(InstructorApplication.class);
        then(instructorApplicationRepository).should().save(applicationCaptor.capture());
        assertThat(applicationCaptor.getValue().getStatus()).isEqualTo(ApplicationStatus.REJECTED);
        assertThat(applicationCaptor.getValue().getRejectionReason()).isEqualTo("기준 미달");

        then(instructorRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("존재하지 않는 신청 ID로 심사하면 예외가 발생한다")
    void review_notFound_throwsException() {
        given(instructorApplicationRepository.findById(99L)).willReturn(Optional.empty());

        ReviewInstructorApplicationCommand command =
                new ReviewInstructorApplicationCommand(99L, ReviewAction.APPROVE, null);

        assertThatThrownBy(() -> reviewInstructorApplicationService.review(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INSTRUCTOR_APPLICATION_NOT_FOUND);
    }

    @Test
    @DisplayName("이미 승인된 신청을 다시 승인하면 400 예외가 발생한다")
    void review_approveAlreadyApproved_throwsBusinessException() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        application.approve(java.time.LocalDateTime.now());
        given(instructorApplicationRepository.findById(1L)).willReturn(Optional.of(application));

        ReviewInstructorApplicationCommand command =
                new ReviewInstructorApplicationCommand(1L, ReviewAction.APPROVE, null);

        assertThatThrownBy(() -> reviewInstructorApplicationService.review(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INSTRUCTOR_APPLICATION_ALREADY_REVIEWED);
    }

    @Test
    @DisplayName("반려 사유 없이 반려하면 400 예외가 발생한다")
    void review_rejectWithoutReason_throwsBusinessException() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        given(instructorApplicationRepository.findById(1L)).willReturn(Optional.of(application));

        ReviewInstructorApplicationCommand command =
                new ReviewInstructorApplicationCommand(1L, ReviewAction.REJECT, null);

        assertThatThrownBy(() -> reviewInstructorApplicationService.review(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INSTRUCTOR_APPLICATION_REJECTION_REASON_REQUIRED);
    }

    @Test
    @DisplayName("action이 null이면 400 예외가 발생한다")
    void review_nullAction_throwsBusinessException() {
        InstructorApplication application = InstructorApplication.apply(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        given(instructorApplicationRepository.findById(1L)).willReturn(Optional.of(application));

        ReviewInstructorApplicationCommand command =
                new ReviewInstructorApplicationCommand(1L, null, null);

        assertThatThrownBy(() -> reviewInstructorApplicationService.review(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_INPUT);
    }
}
