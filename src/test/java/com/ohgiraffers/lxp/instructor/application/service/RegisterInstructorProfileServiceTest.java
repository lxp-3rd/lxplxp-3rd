package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.in.RegisterInstructorProfileUseCase.RegisterInstructorProfileCommand;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorProfileRepository;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorRepository;
import com.ohgiraffers.lxp.instructor.domain.InstructorProfile;
import com.ohgiraffers.lxp.instructor.domain.InstructorStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class RegisterInstructorProfileServiceTest {

    @InjectMocks
    private RegisterInstructorProfileService registerInstructorProfileService;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private InstructorProfileRepository instructorProfileRepository;

    @Test
    @DisplayName("프로필 등록 성공 시 저장소에 저장된다")
    void register_success() {
        RegisterInstructorProfileCommand command = new RegisterInstructorProfileCommand(
                1L, "https://example.com/image.jpg", "10년 경력의 Java 개발자입니다."
        );
        given(instructorRepository.existsByIdAndStatusIn(
                eq(1L), eq(List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED))))
                .willReturn(true);
        given(instructorProfileRepository.findByInstructorId(1L)).willReturn(Optional.empty());

        registerInstructorProfileService.register(command);

        then(instructorProfileRepository).should().save(any(InstructorProfile.class));
    }

    @Test
    @DisplayName("존재하지 않거나 DELETED 강사 ID로 등록하면 예외가 발생한다")
    void register_instructorNotFound_throwsException() {
        RegisterInstructorProfileCommand command = new RegisterInstructorProfileCommand(
                99L, "https://example.com/image.jpg", "자기소개"
        );
        given(instructorRepository.existsByIdAndStatusIn(
                eq(99L), eq(List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED))))
                .willReturn(false);

        assertThatThrownBy(() -> registerInstructorProfileService.register(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INSTRUCTOR_NOT_FOUND);

        then(instructorProfileRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("이미 프로필이 존재하면 예외가 발생한다")
    void register_profileAlreadyExists_throwsException() {
        RegisterInstructorProfileCommand command = new RegisterInstructorProfileCommand(
                1L, "https://example.com/image.jpg", "자기소개"
        );
        given(instructorRepository.existsByIdAndStatusIn(
                eq(1L), eq(List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED))))
                .willReturn(true);
        given(instructorProfileRepository.findByInstructorId(1L))
                .willReturn(Optional.of(InstructorProfile.create(1L, "https://example.com/image.jpg", "기존 자기소개")));

        assertThatThrownBy(() -> registerInstructorProfileService.register(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INSTRUCTOR_PROFILE_ALREADY_EXISTS);

        then(instructorProfileRepository).should(never()).save(any());
    }
}
