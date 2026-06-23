package com.ohgiraffers.lxp.instructor.presentation.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.application.port.out.TokenValidatePort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.command.ApplyInstructorCommand;
import com.ohgiraffers.lxp.instructor.application.port.command.ReviewAction;
import com.ohgiraffers.lxp.instructor.application.port.command.ReviewInstructorApplicationCommand;
import com.ohgiraffers.lxp.instructor.application.port.in.ApplyInstructorUseCase;
import com.ohgiraffers.lxp.instructor.application.port.in.ReviewInstructorApplicationUseCase;
import com.ohgiraffers.lxp.instructor.presentation.dto.ApplyInstructorRequest;
import com.ohgiraffers.lxp.instructor.presentation.dto.ReviewInstructorApplicationRequest;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InstructorApplicationController.class)
class InstructorApplicationControllerTest {

    private static final String LEARNER_TOKEN = "learner-token";
    private static final String ADMIN_TOKEN = "admin-token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ApplyInstructorUseCase applyInstructorUseCase;

    @MockitoBean
    private ReviewInstructorApplicationUseCase reviewInstructorApplicationUseCase;

    @MockitoBean
    private TokenValidatePort tokenValidatePort;

    @BeforeEach
    void setUp() {
        given(tokenValidatePort.validateAccessToken(LEARNER_TOKEN))
                .willReturn(new AuthenticatedMember(1L, MemberRole.LEARNER));
        given(tokenValidatePort.validateAccessToken(ADMIN_TOKEN))
                .willReturn(new AuthenticatedMember(99L, MemberRole.ADMIN));
    }

    @Test
    @DisplayName("강사 신청 성공 시 201을 반환한다")
    void apply_success_returns201() throws Exception {
        ApplyInstructorRequest request = new ApplyInstructorRequest(
                1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );
        willDoNothing().given(applyInstructorUseCase).apply(any(ApplyInstructorCommand.class));

        mockMvc.perform(post("/api/instructors/applications")
                        .header(HttpHeaders.AUTHORIZATION, bearerLearnerToken())
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
                        .header(HttpHeaders.AUTHORIZATION, bearerLearnerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("memberId가 0이면 400을 반환한다")
    void apply_zeroMemberId_returns400() throws Exception {
        ApplyInstructorRequest request = new ApplyInstructorRequest(
                0L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );

        mockMvc.perform(post("/api/instructors/applications")
                        .header(HttpHeaders.AUTHORIZATION, bearerLearnerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("memberId가 음수이면 400을 반환한다")
    void apply_negativeMemberId_returns400() throws Exception {
        ApplyInstructorRequest request = new ApplyInstructorRequest(
                -1L, "홍길동", "10년 경력의 Java 개발자입니다.", "백엔드 개발"
        );

        mockMvc.perform(post("/api/instructors/applications")
                        .header(HttpHeaders.AUTHORIZATION, bearerLearnerToken())
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
                        .header(HttpHeaders.AUTHORIZATION, bearerLearnerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("강사 신청 승인 성공 시 200을 반환한다")
    void review_approve_returns200() throws Exception {
        ReviewInstructorApplicationRequest request =
                new ReviewInstructorApplicationRequest(ReviewAction.APPROVE, null);
        willDoNothing().given(reviewInstructorApplicationUseCase)
                .review(any(ReviewInstructorApplicationCommand.class));

        mockMvc.perform(patch("/api/instructors/applications/1")
                        .header(HttpHeaders.AUTHORIZATION, bearerAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("강사 신청 반려 성공 시 200을 반환한다")
    void review_reject_returns200() throws Exception {
        ReviewInstructorApplicationRequest request =
                new ReviewInstructorApplicationRequest(ReviewAction.REJECT, "기준 미달");
        willDoNothing().given(reviewInstructorApplicationUseCase)
                .review(any(ReviewInstructorApplicationCommand.class));

        mockMvc.perform(patch("/api/instructors/applications/1")
                        .header(HttpHeaders.AUTHORIZATION, bearerAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("존재하지 않는 신청 ID로 심사 시 404를 반환한다")
    void review_notFound_returns404() throws Exception {
        ReviewInstructorApplicationRequest request =
                new ReviewInstructorApplicationRequest(ReviewAction.APPROVE, null);
        willThrow(new BusinessException(ErrorCode.INSTRUCTOR_APPLICATION_NOT_FOUND))
                .given(reviewInstructorApplicationUseCase)
                .review(any(ReviewInstructorApplicationCommand.class));

        mockMvc.perform(patch("/api/instructors/applications/99")
                        .header(HttpHeaders.AUTHORIZATION, bearerAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("action 필드 누락 시 400을 반환한다")
    void review_missingAction_returns400() throws Exception {
        ReviewInstructorApplicationRequest request =
                new ReviewInstructorApplicationRequest(null, null);

        mockMvc.perform(patch("/api/instructors/applications/1")
                        .header(HttpHeaders.AUTHORIZATION, bearerAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("REJECT 시 반려 사유가 없으면 400을 반환한다")
    void review_rejectWithoutReason_returns400() throws Exception {
        ReviewInstructorApplicationRequest request =
                new ReviewInstructorApplicationRequest(ReviewAction.REJECT, null);

        mockMvc.perform(patch("/api/instructors/applications/1")
                        .header(HttpHeaders.AUTHORIZATION, bearerAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    private String bearerLearnerToken() {
        return "Bearer " + LEARNER_TOKEN;
    }

    private String bearerAdminToken() {
        return "Bearer " + ADMIN_TOKEN;
    }
}
