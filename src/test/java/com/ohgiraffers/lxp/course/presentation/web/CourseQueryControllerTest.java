package com.ohgiraffers.lxp.course.presentation.web;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ohgiraffers.lxp.course.application.port.in.GetCourseListUseCase;
import com.ohgiraffers.lxp.course.domain.model.read.CourseSummary;

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
}
