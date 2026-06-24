package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.command.UpdateCourseCommand;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UpdateCourseServiceTest {

    @InjectMocks
    private UpdateCourseService updateCourseService;

    @Mock
    private CourseRepositoryPort courseRepository;

    @Test
    @DisplayName("정상 수정 시 변경된 필드로 저장된다")
    void update_success() {
        Course existing = Course.restore(1L, 10L, "기존 제목", "기존 설명", null, CourseStatus.HIDDEN, null);
        UpdateCourseCommand command = new UpdateCourseCommand(1L, "새 제목", "새 설명", "https://example.com/thumb.jpg");

        given(courseRepository.findById(1L)).willReturn(Optional.of(existing));
        given(courseRepository.save(any(Course.class))).willReturn(existing);

        updateCourseService.update(command);

        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        then(courseRepository).should().save(captor.capture());
        assertThat(captor.getValue().getTitle()).isEqualTo("새 제목");
        assertThat(captor.getValue().getDescription()).isEqualTo("새 설명");
        assertThat(captor.getValue().getThumbnailUrl()).isEqualTo("https://example.com/thumb.jpg");
    }

    @Test
    @DisplayName("존재하지 않는 강좌 ID로 수정하면 예외가 발생한다")
    void update_courseNotFound_throwsException() {
        UpdateCourseCommand command = new UpdateCourseCommand(99L, "새 제목", "새 설명", null);
        given(courseRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> updateCourseService.update(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.COURSE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("빈 제목으로 수정하면 INVALID_INPUT 예외가 발생한다")
    void update_blankTitle_throwsException() {
        Course existing = Course.restore(1L, 10L, "기존 제목", "기존 설명", null, CourseStatus.HIDDEN, null);
        UpdateCourseCommand command = new UpdateCourseCommand(1L, " ", "새 설명", null);
        given(courseRepository.findById(1L)).willReturn(Optional.of(existing));

        assertThatThrownBy(() -> updateCourseService.update(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_INPUT.getMessage());
    }
}
