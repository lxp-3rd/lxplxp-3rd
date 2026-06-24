package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.content.application.port.out.ContentRepositoryPort;
import com.ohgiraffers.lxp.content.domain.model.entity.Content;
import com.ohgiraffers.lxp.course.application.port.command.RegisterCourseCommand;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.course.application.port.out.InstructorValidationPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class RegisterCourseServiceTest {

    @InjectMocks
    private RegisterCourseService registerCourseService;

    @Mock
    private CourseRepositoryPort courseRepository;

    @Mock
    private ContentRepositoryPort contentRepository;

    @Mock
    private InstructorValidationPort instructorValidation;

    @Test
    @DisplayName("정상 등록 시 강좌가 저장되고 ID가 반환된다")
    void register_success() {
        RegisterCourseCommand command = new RegisterCourseCommand(
                1L, "강좌 제목", "강좌 설명", null, List.of()
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
                1L, "강좌 제목", "강좌 설명", null, List.of()
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
    @DisplayName("커리큘럼이 있으면 강좌 등록 후 콘텐츠를 순서대로 저장한다")
    void register_withContents_savesCurriculum() {
        RegisterCourseCommand command = new RegisterCourseCommand(
                1L, "강좌 제목", "강좌 설명", null, List.of("소개", "실습")
        );
        Course saved = Course.restore(10L, 1L, "강좌 제목", "강좌 설명", null, CourseStatus.HIDDEN, null);

        given(instructorValidation.isActiveInstructor(1L)).willReturn(true);
        given(courseRepository.save(any(Course.class))).willReturn(saved);
        given(contentRepository.save(any(Content.class))).willAnswer(invocation -> invocation.getArgument(0));

        Long result = registerCourseService.register(command);

        assertThat(result).isEqualTo(10L);

        ArgumentCaptor<Content> captor = ArgumentCaptor.forClass(Content.class);
        then(contentRepository).should(times(2)).save(captor.capture());
        assertThat(captor.getAllValues())
                .extracting(Content::getCourseId, Content::getSequence, Content::getTitle, Content::getContentUrl)
                .containsExactly(
                        tuple(10L, 1, "소개", "about:blank"),
                        tuple(10L, 2, "실습", "about:blank")
                );
    }

    @Test
    @DisplayName("존재하지 않는 강사 ID로 등록하면 예외가 발생한다")
    void register_instructorNotFound_throwsException() {
        RegisterCourseCommand command = new RegisterCourseCommand(
                99L, "강좌 제목", "강좌 설명", null, List.of()
        );
        given(instructorValidation.isActiveInstructor(99L)).willReturn(false);

        assertThatThrownBy(() -> registerCourseService.register(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INSTRUCTOR_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("입력값이 유효하지 않으면 INVALID_INPUT 예외가 발생한다")
    void register_invalidInput_throwsException() {
        RegisterCourseCommand command = new RegisterCourseCommand(
                1L, " ", "강좌 설명", null, List.of()
        );
        given(instructorValidation.isActiveInstructor(1L)).willReturn(true);

        assertThatThrownBy(() -> registerCourseService.register(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_INPUT.getMessage());
    }
}
