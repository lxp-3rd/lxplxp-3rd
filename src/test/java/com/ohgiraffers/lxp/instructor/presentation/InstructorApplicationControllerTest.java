package com.ohgiraffers.lxp.instructor.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.in.ApplyInstructorUseCase;
import com.ohgiraffers.lxp.instructor.application.port.in.ApplyInstructorUseCase.ApplyInstructorCommand;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InstructorApplicationController.class)
class InstructorApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ApplyInstructorUseCase applyInstructorUseCase;

    @Test
    @DisplayName("강사 신청 성공 시 201을 반환한다")
    void apply_success_returns201() throws Exception {
        ApplyInstructorRequest request = new ApplyInstructorRequest(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        willDoNothing().given(applyInstructorUseCase).apply(any(ApplyInstructorCommand.class));

        mockMvc.perform(post("/api/instructors/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("필수 필드 누락 시 400을 반환한다")
    void apply_missingRequiredField_returns400() throws Exception {
        ApplyInstructorRequest request = new ApplyInstructorRequest(
                null, "", "자기소개", "전문분야"
        );

        mockMvc.perform(post("/api/instructors/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("중복 신청 시 409를 반환한다")
    void apply_duplicate_returns409() throws Exception {
        ApplyInstructorRequest request = new ApplyInstructorRequest(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        willThrow(new BusinessException(ErrorCode.INSTRUCTOR_APPLICATION_ALREADY_EXISTS))
                .given(applyInstructorUseCase).apply(any(ApplyInstructorCommand.class));

        mockMvc.perform(post("/api/instructors/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }
}
