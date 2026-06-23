package com.ohgiraffers.lxp.course.presentation.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.course.application.port.command.RegisterCourseCommand;
import com.ohgiraffers.lxp.course.application.port.in.RegisterCourseUseCase;
import com.ohgiraffers.lxp.course.presentation.dto.RegisterCourseRequest;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegisterCourseUseCase registerCourseUseCase;

    @Test
    @DisplayName("강좌 등록 성공 시 201과 courseId를 반환한다")
    void register_success_returns201() throws Exception {
        RegisterCourseRequest request = new RegisterCourseRequest(
                1L, "강좌 제목", "강좌 설명입니다.", null
        );
        given(registerCourseUseCase.register(any(RegisterCourseCommand.class))).willReturn(42L);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.courseId").value(42L));
    }

    @Test
    @DisplayName("instructorId가 null이면 400을 반환한다")
    void register_nullInstructorId_returns400() throws Exception {
        RegisterCourseRequest request = new RegisterCourseRequest(
                null, "강좌 제목", "강좌 설명입니다.", null
        );

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("title이 빈 문자열이면 400을 반환한다")
    void register_blankTitle_returns400() throws Exception {
        RegisterCourseRequest request = new RegisterCourseRequest(
                1L, "", "강좌 설명입니다.", null
        );

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("thumbnailUrl이 올바른 URL 형식이 아니면 400을 반환한다")
    void register_invalidThumbnailUrl_returns400() throws Exception {
        RegisterCourseRequest request = new RegisterCourseRequest(
                1L, "강좌 제목", "강좌 설명입니다.", "not-a-valid-url"
        );

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 강사 ID로 등록 시 404를 반환한다")
    void register_instructorNotFound_returns404() throws Exception {
        RegisterCourseRequest request = new RegisterCourseRequest(
                99L, "강좌 제목", "강좌 설명입니다.", null
        );
        willThrow(new BusinessException(ErrorCode.INSTRUCTOR_NOT_FOUND))
                .given(registerCourseUseCase).register(any(RegisterCourseCommand.class));

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
