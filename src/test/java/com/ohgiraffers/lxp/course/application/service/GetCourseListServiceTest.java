package com.ohgiraffers.lxp.course.application.service;

import com.ohgiraffers.lxp.course.application.dto.CourseResult;
import com.ohgiraffers.lxp.course.application.port.out.CourseLikeRepositoryPort;
import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetCourseListServiceTest {

    @InjectMocks
    private GetCourseListService getCourseListService;

    @Mock
    private CourseRepositoryPort courseRepository;

    @Mock
    private CourseLikeRepositoryPort courseLikeRepository;

    @Test
    @DisplayName("강좌 목록 조회 시 각 강좌에 likeCount가 포함된 CourseResult 목록을 반환한다")
    void getCourseList_success() {
        Course course1 = Course.restore(1L, 10L, "Java 기초", "자바 강좌", null, CourseStatus.PUBLIC, null);
        Course course2 = Course.restore(2L, 10L, "Spring 기초", "스프링 강좌", null, CourseStatus.HIDDEN, null);
        given(courseRepository.findAll()).willReturn(List.of(course1, course2));
        given(courseLikeRepository.countByCourseId(1L)).willReturn(3L);
        given(courseLikeRepository.countByCourseId(2L)).willReturn(0L);

        List<CourseResult> results = getCourseListService.getCourseList();

        assertThat(results).hasSize(2);
        assertThat(results.get(0).id()).isEqualTo(1L);
        assertThat(results.get(0).likeCount()).isEqualTo(3L);
        assertThat(results.get(1).id()).isEqualTo(2L);
        assertThat(results.get(1).likeCount()).isEqualTo(0L);
    }

    @Test
    @DisplayName("강좌가 없으면 빈 목록을 반환한다")
    void getCourseList_empty() {
        given(courseRepository.findAll()).willReturn(List.of());

        List<CourseResult> results = getCourseListService.getCourseList();

        assertThat(results).isEmpty();
    }
}
