package com.ohgiraffers.lxp.course.presentation.web;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ohgiraffers.lxp.course.application.port.in.GetCourseDetailUseCase;
import com.ohgiraffers.lxp.course.application.port.in.GetCourseListUseCase;
import com.ohgiraffers.lxp.course.domain.model.read.CourseDetail;
import com.ohgiraffers.lxp.course.domain.model.read.CourseSummary;
import com.ohgiraffers.lxp.course.domain.model.read.CurriculumItem;
import com.ohgiraffers.lxp.course.presentation.support.OptionalMemberResolver;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseQueryController.class)
@DisplayName("CourseQueryController 단위 테스트")
class CourseQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetCourseListUseCase getCourseListUseCase;

    @MockitoBean
    private GetCourseDetailUseCase getCourseDetailUseCase;

    @MockitoBean
    private OptionalMemberResolver optionalMemberResolver;

    @Test
    @DisplayName("강좌 목록 조회 시 200과 강사명·수강생 수·좋아요 수를 포함한 배열을 반환한다")
    void getCourseList_success_returns200() throws Exception {
        given(getCourseListUseCase.getCourseList()).willReturn(List.of(
                new CourseSummary(1L, "Java 기초", "thumb1.png", "김태희", 1240L, 88L),
                new CourseSummary(2L, "Spring 기초", null, "이순신", 30L, 5L)
        ));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].instructorName").value("김태희"))
                .andExpect(jsonPath("$[0].enrollmentCount").value(1240L))
                .andExpect(jsonPath("$[0].likeCount").value(88L))
                .andExpect(jsonPath("$[1].thumbnailUrl").doesNotExist())
                .andExpect(jsonPath("$[1].likeCount").value(5L));
    }

    @Test
    @DisplayName("공개 강좌가 없으면 200과 빈 배열을 반환한다")
    void getCourseList_empty_returns200() throws Exception {
        given(getCourseListUseCase.getCourseList()).willReturn(List.of());

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("비로그인 상세 조회 시 200과 enrolled=false, 커리큘럼을 반환한다")
    void getCourseDetail_anonymous_returns200() throws Exception {
        given(optionalMemberResolver.resolveMemberId(any())).willReturn(null);
        given(getCourseDetailUseCase.getCourseDetail(1L, null)).willReturn(
                new CourseDetail(1L, "Java 기초", "자바 입문 강좌", "thumb.png", 1240L, false,
                        List.of(new CurriculumItem(11L, 1, "1장"), new CurriculumItem(12L, 2, "2장")))
        );

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("자바 입문 강좌"))
                .andExpect(jsonPath("$.enrollmentCount").value(1240L))
                .andExpect(jsonPath("$.enrolled").value(false))
                .andExpect(jsonPath("$.curriculum.length()").value(2))
                .andExpect(jsonPath("$.curriculum[0].order").value(1))
                .andExpect(jsonPath("$.curriculum[0].title").value("1장"));
    }

    @Test
    @DisplayName("로그인 사용자가 수강 중이면 enrolled=true를 반환한다")
    void getCourseDetail_loggedIn_enrolled_returns200() throws Exception {
        given(optionalMemberResolver.resolveMemberId(any())).willReturn(7L);
        given(getCourseDetailUseCase.getCourseDetail(1L, 7L)).willReturn(
                new CourseDetail(1L, "Java 기초", "설명", null, 1240L, true, List.of())
        );

        mockMvc.perform(get("/api/courses/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enrolled").value(true));
    }

    @Test
    @DisplayName("PUBLIC이 아니거나 없는 강좌 상세 조회 시 404를 반환한다")
    void getCourseDetail_notFound_returns404() throws Exception {
        given(optionalMemberResolver.resolveMemberId(any())).willReturn(null);
        given(getCourseDetailUseCase.getCourseDetail(999L, null))
                .willThrow(new BusinessException(ErrorCode.COURSE_NOT_FOUND));

        mockMvc.perform(get("/api/courses/999"))
                .andExpect(status().isNotFound());
    }
}
