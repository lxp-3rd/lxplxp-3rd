package com.ohgiraffers.lxp.enrollment.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.application.exception.DuplicateEnrollmentException;
import com.ohgiraffers.lxp.enrollment.application.exception.MemberNotLearnerException;
import com.ohgiraffers.lxp.enrollment.application.port.command.EnrollCommand;
import com.ohgiraffers.lxp.enrollment.application.port.in.EnrollUseCase;
import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.enrollment.presentation.dto.EnrollmentRequest;
import com.ohgiraffers.lxp.enrollment.presentation.web.EnrollmentController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrollmentController.class)
@DisplayName("수강신청 컨트롤러 테스트")
class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnrollUseCase enrollUseCase;

    private String body(Long memberId, Long courseId) throws Exception {
        return objectMapper.writeValueAsString(new EnrollmentRequest(memberId, courseId));
    }

    @Test
    @DisplayName("정상 요청 → 201 Created + Location + 응답 본문")
    void enrollCreated() throws Exception {
        given(enrollUseCase.enroll(any(EnrollCommand.class)))
                .willReturn(new EnrollmentResult(10L, 1L, 2L, EnrollmentStatus.ACTIVE, LocalDateTime.now()));

        mockMvc.perform(post("/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(1L, 2L)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/enrollments/10"))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("memberId 누락 → 400 INVALID_INPUT, 유스케이스 호출 안 함")
    void validationFails() throws Exception {
        mockMvc.perform(post("/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(null, 2L)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"));

        verify(enrollUseCase, never()).enroll(any());
    }

    @Test
    @DisplayName("중복 예외 → 409 ENROLLMENT_ALREADY_EXISTS로 매핑")
    void duplicateMapsToConflict() throws Exception {
        given(enrollUseCase.enroll(any(EnrollCommand.class)))
                .willThrow(new DuplicateEnrollmentException());

        mockMvc.perform(post("/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(1L, 2L)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("ENROLLMENT_ALREADY_EXISTS"));
    }

    @Test
    @DisplayName("수강생 아님 예외 → 403 MEMBER_NOT_LEARNER로 매핑")
    void notLearnerMapsToForbidden() throws Exception {
        given(enrollUseCase.enroll(any(EnrollCommand.class)))
                .willThrow(new MemberNotLearnerException());

        mockMvc.perform(post("/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body(1L, 2L)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("MEMBER_NOT_LEARNER"));
    }
}
