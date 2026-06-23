package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.command.ChangeCourseStatusCommand;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.entity.HiddenBy;
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
class ChangeCourseStatusServiceTest {

    @InjectMocks
    private ChangeCourseStatusService changeCourseStatusService;

    @Mock
    private CourseRepositoryPort courseRepository;

    @Test
    @DisplayName("강사가 PUBLIC 강좌를 HIDDEN으로 변경하면 저장된다")
    void changeStatus_instructorHides_success() {
        Course existing = Course.restore(1L, 10L, "강좌 제목", "강좌 설명", null, CourseStatus.PUBLIC, null);
        ChangeCourseStatusCommand command = new ChangeCourseStatusCommand(1L, CourseStatus.HIDDEN, HiddenBy.INSTRUCTOR);

        given(courseRepository.findById(1L)).willReturn(Optional.of(existing));
        given(courseRepository.save(any(Course.class))).willReturn(existing);

        changeCourseStatusService.changeStatus(command);

        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        then(courseRepository).should().save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(CourseStatus.HIDDEN);
        assertThat(captor.getValue().getHiddenBy()).isEqualTo(HiddenBy.INSTRUCTOR);
    }

    @Test
    @DisplayName("존재하지 않는 강좌 상태 변경 시 COURSE_NOT_FOUND 예외가 발생한다")
    void changeStatus_courseNotFound_throwsException() {
        ChangeCourseStatusCommand command = new ChangeCourseStatusCommand(99L, CourseStatus.HIDDEN, HiddenBy.INSTRUCTOR);
        given(courseRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> changeCourseStatusService.changeStatus(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.COURSE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("강사가 관리자가 숨긴 강좌를 공개하려 하면 COURSE_STATUS_CHANGE_NOT_ALLOWED 예외가 발생한다")
    void changeStatus_instructorCannotUnhideAdminHidden_throwsException() {
        Course existing = Course.restore(1L, 10L, "강좌 제목", "강좌 설명", null, CourseStatus.HIDDEN, HiddenBy.ADMIN);
        ChangeCourseStatusCommand command = new ChangeCourseStatusCommand(1L, CourseStatus.PUBLIC, HiddenBy.INSTRUCTOR);
        given(courseRepository.findById(1L)).willReturn(Optional.of(existing));

        assertThatThrownBy(() -> changeCourseStatusService.changeStatus(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.COURSE_STATUS_CHANGE_NOT_ALLOWED.getMessage());
    }
}
