package com.ohgiraffers.lxp.course.application.service;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ohgiraffers.lxp.course.application.port.out.CourseListView;
import com.ohgiraffers.lxp.course.application.port.out.LoadCourseLikeCountPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadCourseListPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadEnrollmentCountPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadInstructorNamePort;
import com.ohgiraffers.lxp.course.domain.model.read.CourseSummary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetCourseListService 단위 테스트")
class GetCourseListServiceTest {

    @InjectMocks
    private GetCourseListService getCourseListService;

    @Mock
    private LoadCourseListPort loadCourseListPort;

    @Mock
    private LoadEnrollmentCountPort loadEnrollmentCountPort;

    @Mock
    private LoadInstructorNamePort loadInstructorNamePort;

    @Mock
    private LoadCourseLikeCountPort loadCourseLikeCountPort;

    @Test
    @DisplayName("PUBLIC 강좌에 수강생 수·강사명·좋아요 수를 조합해 반환한다")
    void getCourseList_success() {
        CourseListView c1 = new CourseListView(1L, "Java 기초", "thumb1.png", 10L);
        CourseListView c2 = new CourseListView(2L, "Spring 기초", null, 11L);
        given(loadCourseListPort.loadPublicCourses()).willReturn(List.of(c1, c2));
        given(loadEnrollmentCountPort.countByCourseIds(List.of(1L, 2L)))
                .willReturn(Map.of(1L, 1240L, 2L, 30L));
        given(loadInstructorNamePort.findNamesByInstructorIds(List.of(10L, 11L)))
                .willReturn(Map.of(10L, "김태희", 11L, "이순신"));
        given(loadCourseLikeCountPort.countByCourseIds(List.of(1L, 2L)))
                .willReturn(Map.of(1L, 88L, 2L, 5L));

        List<CourseSummary> result = getCourseListService.getCourseList();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(0).instructorName()).isEqualTo("김태희");
        assertThat(result.get(0).enrollmentCount()).isEqualTo(1240L);
        assertThat(result.get(0).likeCount()).isEqualTo(88L);
        assertThat(result.get(1).thumbnailUrl()).isNull();
        assertThat(result.get(1).likeCount()).isEqualTo(5L);
    }

    @Test
    @DisplayName("수강생·좋아요 집계에 없는 강좌는 각각 0으로 채운다")
    void getCourseList_missingCounts_returnsZero() {
        CourseListView c1 = new CourseListView(1L, "Java 기초", null, 10L);
        given(loadCourseListPort.loadPublicCourses()).willReturn(List.of(c1));
        given(loadEnrollmentCountPort.countByCourseIds(List.of(1L))).willReturn(Map.of());
        given(loadInstructorNamePort.findNamesByInstructorIds(List.of(10L))).willReturn(Map.of(10L, "김태희"));
        given(loadCourseLikeCountPort.countByCourseIds(List.of(1L))).willReturn(Map.of());

        List<CourseSummary> result = getCourseListService.getCourseList();

        assertThat(result.get(0).enrollmentCount()).isZero();
        assertThat(result.get(0).likeCount()).isZero();
    }

    @Test
    @DisplayName("PUBLIC 강좌가 없으면 빈 목록을 반환한다")
    void getCourseList_empty() {
        given(loadCourseListPort.loadPublicCourses()).willReturn(List.of());
        given(loadEnrollmentCountPort.countByCourseIds(List.of())).willReturn(Map.of());
        given(loadInstructorNamePort.findNamesByInstructorIds(List.of())).willReturn(Map.of());
        given(loadCourseLikeCountPort.countByCourseIds(List.of())).willReturn(Map.of());

        List<CourseSummary> result = getCourseListService.getCourseList();

        assertThat(result).isEmpty();
    }
}
