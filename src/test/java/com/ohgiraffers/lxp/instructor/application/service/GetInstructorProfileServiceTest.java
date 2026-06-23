package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorProfileRepository;
import com.ohgiraffers.lxp.instructor.domain.InstructorProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetInstructorProfileServiceTest {

    @InjectMocks
    private GetInstructorProfileService getInstructorProfileService;

    @Mock
    private InstructorProfileRepository instructorProfileRepository;

    @Test
    @DisplayName("강사 ID로 프로필을 조회하면 도메인 객체를 반환한다")
    void get_success_returnsProfile() {
        InstructorProfile profile = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "10년 경력의 Java 개발자입니다."
        );
        given(instructorProfileRepository.findByInstructorId(1L)).willReturn(Optional.of(profile));

        InstructorProfile result = getInstructorProfileService.get(1L);

        assertThat(result.getInstructorId()).isEqualTo(1L);
        assertThat(result.getProfileImageUrl()).isEqualTo("https://example.com/image.jpg");
        assertThat(result.getBio()).isEqualTo("10년 경력의 Java 개발자입니다.");
    }

    @Test
    @DisplayName("프로필이 없으면 INSTRUCTOR_PROFILE_NOT_FOUND 예외가 발생한다")
    void get_profileNotFound_throwsException() {
        given(instructorProfileRepository.findByInstructorId(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> getInstructorProfileService.get(99L))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.INSTRUCTOR_PROFILE_NOT_FOUND);
    }
}
