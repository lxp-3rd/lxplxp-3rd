package com.ohgiraffers.lxp.qna.presentation.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.qna.application.dto.QuestionResult;
import com.ohgiraffers.lxp.qna.application.port.in.QuestionUseCase;
import com.ohgiraffers.lxp.qna.domain.model.entity.QuestionStatus;

@WebMvcTest(QuestionController.class)
@DisplayName("QuestionController 단위 테스트")
class QuestionControllerTest {

    private static final long COURSE_ID = 1L;
    private static final long MEMBER_ID = 2L;
    private static final long QUESTION_ID = 3L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private QuestionUseCase questionUseCase;

    // ─── POST /questions ───────────────────────────────────────────────────

    @Test
    @DisplayName("질문 등록 성공 시 201을 반환한다")
    void createQuestion_success_returns201() throws Exception {
        given(questionUseCase.createQuestion(any())).willReturn(questionResult(QUESTION_ID));

        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateBody(COURSE_ID, MEMBER_ID, "제목입니다", "내용입니다"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(QUESTION_ID))
                .andExpect(jsonPath("$.status").value("PUBLISHED"));
    }

    @Test
    @DisplayName("courseId 누락 시 400을 반환한다")
    void createQuestion_missingCourseId_returns400() throws Exception {
        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateBody(null, MEMBER_ID, "제목입니다", "내용입니다"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("제목이 1자(최소 미달)이면 400을 반환한다")
    void createQuestion_titleTooShort_returns400() throws Exception {
        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateBody(COURSE_ID, MEMBER_ID, "짧", "내용입니다"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("제목이 201자(최대 초과)이면 400을 반환한다")
    void createQuestion_titleTooLong_returns400() throws Exception {
        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateBody(COURSE_ID, MEMBER_ID, "가".repeat(201), "내용입니다"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("내용이 501자(최대 초과)이면 400을 반환한다")
    void createQuestion_contentTooLong_returns400() throws Exception {
        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateBody(COURSE_ID, MEMBER_ID, "제목입니다", "가".repeat(501)))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("수강하지 않은 강좌에 질문 등록 시 404를 반환한다")
    void createQuestion_notEnrolled_returns404() throws Exception {
        given(questionUseCase.createQuestion(any()))
                .willThrow(new BusinessException(ErrorCode.ENROLLMENT_NOT_FOUND));

        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateBody(COURSE_ID, MEMBER_ID, "제목입니다", "내용입니다"))))
                .andExpect(status().isNotFound());
    }

    // ─── GET /questions ────────────────────────────────────────────────────

    @Test
    @DisplayName("강좌별 질문 목록 조회 시 200과 목록을 반환한다")
    void getQuestions_success_returns200() throws Exception {
        given(questionUseCase.getQuestions(COURSE_ID))
                .willReturn(List.of(questionResult(1L), questionResult(2L)));

        mockMvc.perform(get("/questions").param("courseId", String.valueOf(COURSE_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("courseId 파라미터 없이 목록 조회 시 400을 반환한다")
    void getQuestions_missingCourseId_returns400() throws Exception {
        mockMvc.perform(get("/questions"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 강좌 목록 조회 시 404를 반환한다")
    void getQuestions_courseNotFound_returns404() throws Exception {
        given(questionUseCase.getQuestions(COURSE_ID))
                .willThrow(new BusinessException(ErrorCode.COURSE_NOT_FOUND));

        mockMvc.perform(get("/questions").param("courseId", String.valueOf(COURSE_ID)))
                .andExpect(status().isNotFound());
    }

    // ─── GET /questions/{id} ───────────────────────────────────────────────

    @Test
    @DisplayName("질문 단건 조회 성공 시 200을 반환한다")
    void getQuestion_success_returns200() throws Exception {
        given(questionUseCase.getQuestion(QUESTION_ID)).willReturn(questionResult(QUESTION_ID));

        mockMvc.perform(get("/questions/{id}", QUESTION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(QUESTION_ID));
    }

    @Test
    @DisplayName("존재하지 않는 질문 조회 시 404를 반환한다")
    void getQuestion_notFound_returns404() throws Exception {
        given(questionUseCase.getQuestion(QUESTION_ID))
                .willThrow(new BusinessException(ErrorCode.QUESTION_NOT_FOUND));

        mockMvc.perform(get("/questions/{id}", QUESTION_ID))
                .andExpect(status().isNotFound());
    }

    // ─── PUT /questions/{id} ───────────────────────────────────────────────

    @Test
    @DisplayName("질문 수정 성공 시 200을 반환한다")
    void updateQuestion_success_returns200() throws Exception {
        given(questionUseCase.updateQuestion(any())).willReturn(questionResult(QUESTION_ID));

        mockMvc.perform(put("/questions/{id}", QUESTION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UpdateBody(MEMBER_ID, "수정된 제목", "수정된 내용"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(QUESTION_ID));
    }

    @Test
    @DisplayName("수정 시 제목이 blank이면 400을 반환한다")
    void updateQuestion_blankTitle_returns400() throws Exception {
        mockMvc.perform(put("/questions/{id}", QUESTION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UpdateBody(MEMBER_ID, "  ", "수정된 내용"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 질문 수정 시 404를 반환한다")
    void updateQuestion_notFound_returns404() throws Exception {
        given(questionUseCase.updateQuestion(any()))
                .willThrow(new BusinessException(ErrorCode.QUESTION_NOT_FOUND));

        mockMvc.perform(put("/questions/{id}", QUESTION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UpdateBody(MEMBER_ID, "수정된 제목", "수정된 내용"))))
                .andExpect(status().isNotFound());
    }

    // ─── DELETE /questions/{id} ────────────────────────────────────────────

    @Test
    @DisplayName("질문 삭제 성공 시 204를 반환한다")
    void deleteQuestion_success_returns204() throws Exception {
        willDoNothing().given(questionUseCase).deleteQuestion(eq(QUESTION_ID), eq(MEMBER_ID));

        mockMvc.perform(delete("/questions/{id}", QUESTION_ID)
                        .param("memberId", String.valueOf(MEMBER_ID)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("memberId 파라미터 없이 삭제 요청 시 400을 반환한다")
    void deleteQuestion_missingMemberId_returns400() throws Exception {
        mockMvc.perform(delete("/questions/{id}", QUESTION_ID))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 질문 삭제 시 404를 반환한다")
    void deleteQuestion_notFound_returns404() throws Exception {
        willThrow(new BusinessException(ErrorCode.QUESTION_NOT_FOUND))
                .given(questionUseCase).deleteQuestion(eq(QUESTION_ID), eq(MEMBER_ID));

        mockMvc.perform(delete("/questions/{id}", QUESTION_ID)
                        .param("memberId", String.valueOf(MEMBER_ID)))
                .andExpect(status().isNotFound());
    }

    // ─── helpers ──────────────────────────────────────────────────────────

    private QuestionResult questionResult(Long id) {
        return new QuestionResult(id, COURSE_ID, MEMBER_ID, "제목입니다", "내용입니다",
                QuestionStatus.PUBLISHED, LocalDateTime.now(), LocalDateTime.now());
    }

    record CreateBody(Long courseId, Long memberId, String title, String content) {}
    record UpdateBody(Long memberId, String title, String content) {}
}
