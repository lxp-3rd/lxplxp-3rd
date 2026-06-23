package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class DeleteCourseServiceTest {

    @InjectMocks
    private DeleteCourseService deleteCourseService;

    @Mock
    private CourseRepositoryPort courseRepository;

    @Test
    @DisplayName("정상 삭제 시 courseRepository.delete()가 호출된다")
    void delete_success() {
        Course existing = Course.restore(1L, 10L, "강좌 제목", "강좌 설명", null, CourseStatus.HIDDEN, null);
        given(courseRepository.findById(1L)).willReturn(Optional.of(existing));

        deleteCourseService.delete(1L);

        then(courseRepository).should().delete(1L);
    }

    @Test
    @DisplayName("존재하지 않는 강좌 ID로 삭제하면 예외가 발생한다")
    void delete_courseNotFound_throwsException() {
        given(courseRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> deleteCourseService.delete(99L))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.COURSE_NOT_FOUND.getMessage());
    }
}
