package com.ohgiraffers.lxp.course.application.service;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ohgiraffers.lxp.course.application.dto.AdminCourseResult;
import com.ohgiraffers.lxp.course.application.port.out.AdminCourseListView;
import com.ohgiraffers.lxp.course.application.port.out.LoadCourseListPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadEnrollmentCountPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadInstructorNamePort;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.entity.HiddenBy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetAdminCourseListService 단위 테스트")
class GetAdminCourseListServiceTest {

    @InjectMocks
    private GetAdminCourseListService getAdminCourseListService;

    @Mock
    private LoadCourseListPort loadCourseListPort;

    @Mock
    private LoadEnrollmentCountPort loadEnrollmentCountPort;

    @Mock
    private LoadInstructorNamePort loadInstructorNamePort;

    @Test
    @DisplayName("상태 무관 전체 강좌에 강사명·수강생 수를 조합하고 status/hiddenBy를 노출한다")
    void getAdminCourseList_success() {
        AdminCourseListView c1 =
                new AdminCourseListView(1L, "Java 기초", "thumb1.png", 10L, CourseStatus.PUBLIC, null);
        AdminCourseListView c2 =
                new AdminCourseListView(2L, "Spring 심화", null, 11L, CourseStatus.HIDDEN, HiddenBy.ADMIN);
        given(loadCourseListPort.loadAllCourses()).willReturn(List.of(c1, c2));
        given(loadEnrollmentCountPort.countByCourseIds(List.of(1L, 2L)))
                .willReturn(Map.of(1L, 1240L, 2L, 30L));
        given(loadInstructorNamePort.findNamesByInstructorIds(List.of(10L, 11L)))
                .willReturn(Map.of(10L, "김태희", 11L, "이순신"));

        List<AdminCourseResult> result = getAdminCourseListService.getAdminCourseList();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(0).instructorName()).isEqualTo("김태희");
        assertThat(result.get(0).enrollmentCount()).isEqualTo(1240L);
        assertThat(result.get(0).status()).isEqualTo(CourseStatus.PUBLIC);
        assertThat(result.get(0).hiddenBy()).isNull();
        assertThat(result.get(1).status()).isEqualTo(CourseStatus.HIDDEN);
        assertThat(result.get(1).hiddenBy()).isEqualTo(HiddenBy.ADMIN);
    }

    @Test
    @DisplayName("수강생 집계에 없는 강좌는 0으로 채운다")
    void getAdminCourseList_missingEnrollmentCount_returnsZero() {
        AdminCourseListView c1 =
                new AdminCourseListView(1L, "Java 기초", null, 10L, CourseStatus.CLOSED, null);
        given(loadCourseListPort.loadAllCourses()).willReturn(List.of(c1));
        given(loadEnrollmentCountPort.countByCourseIds(List.of(1L))).willReturn(Map.of());
        given(loadInstructorNamePort.findNamesByInstructorIds(List.of(10L))).willReturn(Map.of(10L, "김태희"));

        List<AdminCourseResult> result = getAdminCourseListService.getAdminCourseList();

        assertThat(result.get(0).enrollmentCount()).isZero();
        assertThat(result.get(0).status()).isEqualTo(CourseStatus.CLOSED);
    }

    @Test
    @DisplayName("강좌가 없으면 빈 목록을 반환한다")
    void getAdminCourseList_empty() {
        given(loadCourseListPort.loadAllCourses()).willReturn(List.of());
        given(loadEnrollmentCountPort.countByCourseIds(List.of())).willReturn(Map.of());
        given(loadInstructorNamePort.findNamesByInstructorIds(List.of())).willReturn(Map.of());

        List<AdminCourseResult> result = getAdminCourseListService.getAdminCourseList();

        assertThat(result).isEmpty();
    }
}