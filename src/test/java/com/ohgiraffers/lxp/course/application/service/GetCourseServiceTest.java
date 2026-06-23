package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.dto.CourseResult;
import com.ohgiraffers.lxp.course.application.port.out.CourseLikeRepositoryPort;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetCourseServiceTest {

    @InjectMocks
    private GetCourseService getCourseService;

    @Mock
    private CourseRepositoryPort courseRepository;

    @Mock
    private CourseLikeRepositoryPort courseLikeRepository;

    @Test
    @DisplayName("강좌 단건 조회 성공 시 CourseResult를 반환한다")
    void getCourse_success() {
        Course course = Course.restore(1L, 10L, "Java 기초", "자바 강좌", null, CourseStatus.PUBLIC, null);
        given(courseRepository.findById(1L)).willReturn(Optional.of(course));
        given(courseLikeRepository.countByCourseId(1L)).willReturn(5L);

        CourseResult result = getCourseService.getCourse(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.instructorId()).isEqualTo(10L);
        assertThat(result.title()).isEqualTo("Java 기초");
        assertThat(result.status()).isEqualTo(CourseStatus.PUBLIC);
        assertThat(result.likeCount()).isEqualTo(5L);
    }

    @Test
    @DisplayName("존재하지 않는 강좌 조회 시 COURSE_NOT_FOUND 예외가 발생한다")
    void getCourse_notFound_throwsException() {
        given(courseRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> getCourseService.getCourse(999L))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.COURSE_NOT_FOUND.getMessage());
    }
}
