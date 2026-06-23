package com.ohgiraffers.lxp.content.presentation.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.content.application.port.command.RegisterContentCommand;
import com.ohgiraffers.lxp.content.application.port.in.RegisterContentUseCase;
import com.ohgiraffers.lxp.content.presentation.dto.RegisterContentRequest;
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

@WebMvcTest(ContentController.class)
class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegisterContentUseCase registerContentUseCase;

    @Test
    @DisplayName("콘텐츠 등록 성공 시 201과 contentId를 반환한다")
    void register_success_returns201() throws Exception {
        RegisterContentRequest request = new RegisterContentRequest(0, "Java 기초", "https://example.com/video");
        given(registerContentUseCase.register(any(RegisterContentCommand.class))).willReturn(10L);

        mockMvc.perform(post("/api/courses/1/contents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contentId").value(10L));
    }

    @Test
    @DisplayName("sequence가 null이면 400을 반환한다")
    void register_nullSequence_returns400() throws Exception {
        RegisterContentRequest request = new RegisterContentRequest(null, "Java 기초", "https://example.com/video");

        mockMvc.perform(post("/api/courses/1/contents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("title이 공백이면 400을 반환한다")
    void register_blankTitle_returns400() throws Exception {
        RegisterContentRequest request = new RegisterContentRequest(0, " ", "https://example.com/video");

        mockMvc.perform(post("/api/courses/1/contents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("contentUrl이 URL 형식이 아니면 400을 반환한다")
    void register_invalidContentUrl_returns400() throws Exception {
        RegisterContentRequest request = new RegisterContentRequest(0, "Java 기초", "not-a-url");

        mockMvc.perform(post("/api/courses/1/contents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("sequence가 음수이면 400을 반환한다")
    void register_negativeSequence_returns400() throws Exception {
        RegisterContentRequest request = new RegisterContentRequest(-1, "Java 기초", "https://example.com/video");

        mockMvc.perform(post("/api/courses/1/contents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 강좌 ID로 등록 시 404를 반환한다")
    void register_courseNotFound_returns404() throws Exception {
        RegisterContentRequest request = new RegisterContentRequest(0, "Java 기초", "https://example.com/video");
        willThrow(new BusinessException(ErrorCode.COURSE_NOT_FOUND))
                .given(registerContentUseCase).register(any(RegisterContentCommand.class));

        mockMvc.perform(post("/api/courses/99/contents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
