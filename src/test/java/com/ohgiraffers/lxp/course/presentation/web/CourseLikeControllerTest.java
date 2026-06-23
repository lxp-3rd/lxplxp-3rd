package com.ohgiraffers.lxp.course.presentation.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.course.application.port.command.LikeCourseCommand;
import com.ohgiraffers.lxp.course.application.port.command.UnlikeCourseCommand;
import com.ohgiraffers.lxp.course.application.port.in.LikeCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.in.UnlikeCourseUseCase;
import com.ohgiraffers.lxp.course.presentation.dto.LikeCourseRequest;
import com.ohgiraffers.lxp.course.presentation.dto.UnlikeCourseRequest;
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
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseLikeController.class)
class CourseLikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LikeCourseUseCase likeCourseUseCase;

    @MockitoBean
    private UnlikeCourseUseCase unlikeCourseUseCase;

    @Test
    @DisplayName("좋아요 등록 성공 시 201을 반환한다")
    void like_success_returns201() throws Exception {
        LikeCourseRequest request = new LikeCourseRequest(100L);
        willDoNothing().given(likeCourseUseCase).like(any(LikeCourseCommand.class));

        mockMvc.perform(post("/api/courses/1/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("learnerId가 null이면 400을 반환한다")
    void like_nullLearnerId_returns400() throws Exception {
        LikeCourseRequest request = new LikeCourseRequest(null);

        mockMvc.perform(post("/api/courses/1/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("이미 좋아요를 눌렀으면 409를 반환한다")
    void like_alreadyExists_returns409() throws Exception {
        LikeCourseRequest request = new LikeCourseRequest(100L);
        willThrow(new BusinessException(ErrorCode.COURSE_LIKE_ALREADY_EXISTS))
                .given(likeCourseUseCase).like(any(LikeCourseCommand.class));

        mockMvc.perform(post("/api/courses/1/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("좋아요 취소 성공 시 204를 반환한다")
    void unlike_success_returns204() throws Exception {
        UnlikeCourseRequest request = new UnlikeCourseRequest(100L);
        willDoNothing().given(unlikeCourseUseCase).unlike(any(UnlikeCourseCommand.class));

        mockMvc.perform(delete("/api/courses/1/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("좋아요 내역이 없으면 404를 반환한다")
    void unlike_notFound_returns404() throws Exception {
        UnlikeCourseRequest request = new UnlikeCourseRequest(100L);
        willThrow(new BusinessException(ErrorCode.COURSE_LIKE_NOT_FOUND))
                .given(unlikeCourseUseCase).unlike(any(UnlikeCourseCommand.class));

        mockMvc.perform(delete("/api/courses/1/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
