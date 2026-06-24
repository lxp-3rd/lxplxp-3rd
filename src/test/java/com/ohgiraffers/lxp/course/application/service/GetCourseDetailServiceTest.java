package com.ohgiraffers.lxp.course.application.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ohgiraffers.lxp.course.application.port.out.CourseDetailView;
import com.ohgiraffers.lxp.course.application.port.out.EnrollmentStatusView;
import com.ohgiraffers.lxp.course.application.port.out.LoadCourseDetailPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadEnrollmentStatusPort;
import com.ohgiraffers.lxp.course.application.port.out.LoadInstructorNamePort;
import com.ohgiraffers.lxp.course.domain.model.read.CourseDetail;
import com.ohgiraffers.lxp.course.domain.model.read.CurriculumItem;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetCourseDetailService 단위 테스트")
class GetCourseDetailServiceTest {

    @InjectMocks
    private GetCourseDetailService getCourseDetailService;

    @Mock
    private LoadCourseDetailPort loadCourseDetailPort;

    @Mock
    private LoadEnrollmentStatusPort loadEnrollmentStatusPort;

    @Mock
    private LoadInstructorNamePort loadInstructorNamePort;

    @Test
    @DisplayName("PUBLIC 강좌 상세에 수강 인원·수강 여부·커리큘럼을 조합해 반환한다")
    void getCourseDetail_loggedIn_enrolled() {
        CourseDetailView view = new CourseDetailView(1L, 10L, "Java 기초", "자바 입문 강좌", "자바 상세 설명", "thumb.png",
                List.of(new CurriculumItem(11L, 1, "1장"), new CurriculumItem(12L, 2, "2장")));
        given(loadCourseDetailPort.loadPublicCourseDetail(1L)).willReturn(Optional.of(view));
        given(loadEnrollmentStatusPort.load(1L, 7L)).willReturn(new EnrollmentStatusView(1240L, true));
        given(loadInstructorNamePort.findNamesByInstructorIds(List.of(10L))).willReturn(Map.of(10L, "김강사"));

        CourseDetail detail = getCourseDetailService.getCourseDetail(1L, 7L);

        assertThat(detail.id()).isEqualTo(1L);
        assertThat(detail.instructorId()).isEqualTo(10L);
        assertThat(detail.instructorName()).isEqualTo("김강사");
        assertThat(detail.summary()).isEqualTo("자바 입문 강좌");
        assertThat(detail.description()).isEqualTo("자바 상세 설명");
        assertThat(detail.enrollmentCount()).isEqualTo(1240L);
        assertThat(detail.enrolled()).isTrue();
        assertThat(detail.curriculum()).hasSize(2);
        assertThat(detail.curriculum().get(0).order()).isEqualTo(1);
        assertThat(detail.curriculum().get(0).title()).isEqualTo("1장");
    }

    @Test
    @DisplayName("비로그인(memberId=null)이면 enrolled=false로 조합한다")
    void getCourseDetail_anonymous_notEnrolled() {
        CourseDetailView view = new CourseDetailView(1L, 10L, "Java 기초", "요약", null, null, List.of());
        given(loadCourseDetailPort.loadPublicCourseDetail(1L)).willReturn(Optional.of(view));
        given(loadEnrollmentStatusPort.load(1L, null)).willReturn(new EnrollmentStatusView(0L, false));
        given(loadInstructorNamePort.findNamesByInstructorIds(List.of(10L))).willReturn(Map.of());

        CourseDetail detail = getCourseDetailService.getCourseDetail(1L, null);

        assertThat(detail.enrolled()).isFalse();
        assertThat(detail.summary()).isEqualTo("요약");
        assertThat(detail.description()).isNull();
        assertThat(detail.curriculum()).isEmpty();
    }

    @Test
    @DisplayName("PUBLIC·미삭제 강좌가 없으면 COURSE_NOT_FOUND 예외")
    void getCourseDetail_notFound() {
        given(loadCourseDetailPort.loadPublicCourseDetail(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> getCourseDetailService.getCourseDetail(99L, null))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.COURSE_NOT_FOUND.getMessage());
    }
}
