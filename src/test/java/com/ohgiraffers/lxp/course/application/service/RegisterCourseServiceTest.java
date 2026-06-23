package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.command.RegisterCourseCommand;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.course.application.port.out.InstructorValidationPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RegisterCourseServiceTest {

    @InjectMocks
    private RegisterCourseService registerCourseService;

    @Mock
    private CourseRepositoryPort courseRepository;

    @Mock
    private InstructorValidationPort instructorValidation;

    @Test
    @DisplayName("정상 등록 시 강좌가 저장되고 ID가 반환된다")
    void register_success() {
        RegisterCourseCommand command = new RegisterCourseCommand(
                1L, "강좌 제목", "강좌 설명", null
        );
        Course saved = Course.restore(10L, 1L, "강좌 제목", "강좌 설명", null, CourseStatus.HIDDEN, null);

        given(instructorValidation.isActiveInstructor(1L)).willReturn(true);
        given(courseRepository.save(any(Course.class))).willReturn(saved);

        Long result = registerCourseService.register(command);

        assertThat(result).isEqualTo(10L);
    }

    @Test
    @DisplayName("정상 등록 시 저장되는 강좌의 초기 상태는 HIDDEN이다")
    void register_savedCourseHasHiddenStatus() {
        RegisterCourseCommand command = new RegisterCourseCommand(
                1L, "강좌 제목", "강좌 설명", null
        );
        Course saved = Course.restore(10L, 1L, "강좌 제목", "강좌 설명", null, CourseStatus.HIDDEN, null);

        given(instructorValidation.isActiveInstructor(1L)).willReturn(true);
        given(courseRepository.save(any(Course.class))).willReturn(saved);

        registerCourseService.register(command);

        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        then(courseRepository).should().save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(CourseStatus.HIDDEN);
    }

    @Test
    @DisplayName("존재하지 않는 강사 ID로 등록하면 예외가 발생한다")
    void register_instructorNotFound_throwsException() {
        RegisterCourseCommand command = new RegisterCourseCommand(
                99L, "강좌 제목", "강좌 설명", null
        );
        given(instructorValidation.isActiveInstructor(99L)).willReturn(false);

        assertThatThrownBy(() -> registerCourseService.register(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INSTRUCTOR_NOT_FOUND.getMessage());
    }
}
