package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.command.UpdateInstructorProfileCommand;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorProfileRepositoryPort;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import org.mockito.ArgumentCaptor;

@ExtendWith(MockitoExtension.class)
class UpdateInstructorProfileServiceTest {

    @InjectMocks
    private UpdateInstructorProfileService updateInstructorProfileService;

    @Mock
    private InstructorProfileRepositoryPort instructorProfileRepository;

    @Test
    @DisplayName("프로필 수정 성공 시 변경된 값이 저장된다")
    void update_success() {
        UpdateInstructorProfileCommand command = new UpdateInstructorProfileCommand(
                1L, "https://example.com/new.jpg", "새로운 자기소개"
        );
        InstructorProfile existing = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "기존 자기소개"
        );
        given(instructorProfileRepository.findByInstructorId(1L)).willReturn(Optional.of(existing));

        updateInstructorProfileService.update(command);

        ArgumentCaptor<InstructorProfile> captor = forClass(InstructorProfile.class);
        then(instructorProfileRepository).should().save(captor.capture());
        assertThat(captor.getValue().getProfileImageUrl()).isEqualTo("https://example.com/new.jpg");
        assertThat(captor.getValue().getBio()).isEqualTo("새로운 자기소개");
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

    @Test
    @DisplayName("빈 profileImageUrl로 수정하면 400 예외가 발생한다")
    void update_blankProfileImageUrl_throwsBusinessException() {
        UpdateInstructorProfileCommand command = new UpdateInstructorProfileCommand(
                1L, "  ", "새로운 자기소개"
        );
        InstructorProfile existing = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "기존 자기소개"
        );
        given(instructorProfileRepository.findByInstructorId(1L)).willReturn(Optional.of(existing));

        assertThatThrownBy(() -> updateInstructorProfileService.update(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_INPUT);
    }

    @Test
    @DisplayName("빈 bio로 수정하면 400 예외가 발생한다")
    void update_blankBio_throwsBusinessException() {
        UpdateInstructorProfileCommand command = new UpdateInstructorProfileCommand(
                1L, "https://example.com/new.jpg", "  "
        );
        InstructorProfile existing = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "기존 자기소개"
        );
        given(instructorProfileRepository.findByInstructorId(1L)).willReturn(Optional.of(existing));

        assertThatThrownBy(() -> updateInstructorProfileService.update(command))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_INPUT);
    }
}
