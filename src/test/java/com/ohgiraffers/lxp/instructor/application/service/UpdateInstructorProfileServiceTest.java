package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.in.UpdateInstructorProfileUseCase.UpdateInstructorProfileCommand;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorProfileRepository;
import com.ohgiraffers.lxp.instructor.domain.InstructorProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UpdateInstructorProfileServiceTest {

    @InjectMocks
    private UpdateInstructorProfileService updateInstructorProfileService;

    @Mock
    private InstructorProfileRepository instructorProfileRepository;

    @Test
    @DisplayName("프로필 수정 성공 시 변경된 내용이 저장된다")
    void update_success() {
        UpdateInstructorProfileCommand command = new UpdateInstructorProfileCommand(
                1L, "https://example.com/new.jpg", "새로운 자기소개"
        );
        InstructorProfile existing = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "기존 자기소개"
        );
        given(instructorProfileRepository.findByInstructorId(1L)).willReturn(Optional.of(existing));

        updateInstructorProfileService.update(command);

        then(instructorProfileRepository).should().save(any(InstructorProfile.class));
    }

    @Test
    @DisplayName("프로필이 존재하지 않으면 예외가 발생한다")
    void update_profileNotFound_throwsException() {
        UpdateInstructorProfileCommand command = new UpdateInstructorProfileCommand(
                99L, "https://example.com/new.jpg", "새로운 자기소개"
        );
        given(instructorProfileRepository.findByInstructorId(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> updateInstructorProfileService.update(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INSTRUCTOR_PROFILE_NOT_FOUND);
    }
}
