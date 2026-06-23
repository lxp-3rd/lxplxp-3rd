package com.ohgiraffers.lxp.content.presentation.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.content.application.port.command.RegisterContentCommand;
import com.ohgiraffers.lxp.content.application.port.command.UpdateContentCommand;
import com.ohgiraffers.lxp.content.application.port.in.DeleteContentUseCase;
import com.ohgiraffers.lxp.content.application.port.in.RegisterContentUseCase;
import com.ohgiraffers.lxp.content.application.port.in.UpdateContentUseCase;
import com.ohgiraffers.lxp.content.presentation.dto.RegisterContentRequest;
import com.ohgiraffers.lxp.content.presentation.dto.UpdateContentRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @MockitoBean
    private UpdateContentUseCase updateContentUseCase;

    @MockitoBean
    private DeleteContentUseCase deleteContentUseCase;

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

    @Test
    @DisplayName("콘텐츠 수정 성공 시 200을 반환한다")
    void update_success_returns200() throws Exception {
        UpdateContentRequest request = new UpdateContentRequest("새 제목", "https://example.com/new");

        mockMvc.perform(put("/api/courses/1/contents/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("수정 시 title이 공백이면 400을 반환한다")
    void update_blankTitle_returns400() throws Exception {
        UpdateContentRequest request = new UpdateContentRequest(" ", "https://example.com/new");

        mockMvc.perform(put("/api/courses/1/contents/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("수정 시 contentUrl이 URL 형식이 아니면 400을 반환한다")
    void update_invalidUrl_returns400() throws Exception {
        UpdateContentRequest request = new UpdateContentRequest("제목", "not-a-url");

        mockMvc.perform(put("/api/courses/1/contents/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 콘텐츠 수정 시 404를 반환한다")
    void update_contentNotFound_returns404() throws Exception {
        UpdateContentRequest request = new UpdateContentRequest("제목", "https://example.com/new");
        willThrow(new BusinessException(ErrorCode.CONTENT_NOT_FOUND))
                .given(updateContentUseCase).update(any(UpdateContentCommand.class));

        mockMvc.perform(put("/api/courses/1/contents/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("콘텐츠 삭제 성공 시 204를 반환한다")
    void delete_success_returns204() throws Exception {
        mockMvc.perform(delete("/api/courses/1/contents/10"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("존재하지 않는 콘텐츠 삭제 시 404를 반환한다")
    void delete_contentNotFound_returns404() throws Exception {
        willThrow(new BusinessException(ErrorCode.CONTENT_NOT_FOUND))
                .given(deleteContentUseCase).delete(99L);

        mockMvc.perform(delete("/api/courses/1/contents/99"))
                .andExpect(status().isNotFound());
    }
}
