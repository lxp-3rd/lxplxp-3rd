package com.ohgiraffers.lxp.roadmap.presentation.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.roadmap.application.dto.RoadmapResult;
import com.ohgiraffers.lxp.roadmap.application.port.in.RoadmapUseCase;
import com.ohgiraffers.lxp.roadmap.presentation.dto.RoadmapRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoadmapController.class)
@DisplayName("RoadmapController 단위 테스트")
class RoadmapControllerTest {

    private static final Long MEMBER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RoadmapUseCase roadmapUseCase;

    @Test
    @DisplayName("로그인 정보 없이 로드맵 생성 시 401을 반환한다")
    void create_withoutLoginMember() throws Exception {
        mockMvc.perform(post("/api/roadmaps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("TOKEN_REQUIRED"));
    }

    @Test
    @DisplayName("로드맵 생성 성공 시 201과 Location을 반환한다")
    void create_success() throws Exception {
        given(roadmapUseCase.createRoadmap(any())).willReturn(result(1L));

        mockMvc.perform(post("/api/roadmaps")
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/roadmaps/1"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.memberId").value(MEMBER_ID))
                .andExpect(jsonPath("$.courseIds.length()").value(2));
    }

    @Test
    @DisplayName("로드맵 명이 2자 미만이면 400을 반환한다")
    void create_nameTooShort() throws Exception {
        RoadmapRequest request = new RoadmapRequest(
                "백",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 2L)
        );

        mockMvc.perform(post("/api/roadmaps")
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로드맵 소개가 20자 미만이면 400을 반환한다")
    void create_introductionTooShort() throws Exception {
        RoadmapRequest request = new RoadmapRequest("백엔드 로드맵", "짧은 소개", List.of(1L, 2L));

        mockMvc.perform(post("/api/roadmaps")
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("강좌가 2개 미만이면 400을 반환한다")
    void create_courseCountTooSmall() throws Exception {
        RoadmapRequest request = new RoadmapRequest(
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L)
        );

        mockMvc.perform(post("/api/roadmaps")
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("강좌 ID가 중복되면 400을 반환한다")
    void create_duplicateCourseIds() throws Exception {
        RoadmapRequest request = new RoadmapRequest(
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 1L)
        );

        mockMvc.perform(post("/api/roadmaps")
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 강좌가 있으면 404를 반환한다")
    void create_courseNotFound() throws Exception {
        given(roadmapUseCase.createRoadmap(any()))
                .willThrow(new BusinessException(ErrorCode.COURSE_NOT_FOUND));

        mockMvc.perform(post("/api/roadmaps")
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("COURSE_NOT_FOUND"));
    }

    @Test
    @DisplayName("로드맵 단건 조회 성공 시 200을 반환한다")
    void get_success() throws Exception {
        given(roadmapUseCase.getRoadmap(1L, MEMBER_ID)).willReturn(result(1L));

        mockMvc.perform(get("/api/roadmaps/{id}", 1L)
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.memberId").value(MEMBER_ID));
    }

    @Test
    @DisplayName("로드맵 목록 조회 성공 시 200을 반환한다")
    void getAll_success() throws Exception {
        given(roadmapUseCase.getRoadmaps(MEMBER_ID)).willReturn(List.of(result(1L), result(2L)));

        mockMvc.perform(get("/api/roadmaps")
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("로드맵 수정 성공 시 200을 반환한다")
    void update_success() throws Exception {
        given(roadmapUseCase.updateRoadmap(any())).willReturn(result(1L));

        mockMvc.perform(put("/api/roadmaps/{id}", 1L)
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("존재하지 않는 로드맵 수정 시 404를 반환한다")
    void update_notFound() throws Exception {
        given(roadmapUseCase.updateRoadmap(any()))
                .willThrow(new BusinessException(ErrorCode.ROADMAP_NOT_FOUND));

        mockMvc.perform(put("/api/roadmaps/{id}", 99L)
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("ROADMAP_NOT_FOUND"));
    }

    @Test
    @DisplayName("다른 회원의 로드맵 수정 시 403을 반환한다")
    void update_forbidden() throws Exception {
        given(roadmapUseCase.updateRoadmap(any()))
                .willThrow(new BusinessException(ErrorCode.FORBIDDEN));

        mockMvc.perform(put("/api/roadmaps/{id}", 1L)
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("FORBIDDEN"));
    }

    @Test
    @DisplayName("로드맵 삭제 성공 시 204를 반환한다")
    void delete_success() throws Exception {
        willDoNothing().given(roadmapUseCase).deleteRoadmap(1L, MEMBER_ID);

        mockMvc.perform(delete("/api/roadmaps/{id}", 1L)
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("존재하지 않는 로드맵 삭제 시 404를 반환한다")
    void delete_notFound() throws Exception {
        willThrow(new BusinessException(ErrorCode.ROADMAP_NOT_FOUND))
                .given(roadmapUseCase).deleteRoadmap(99L, MEMBER_ID);

        mockMvc.perform(delete("/api/roadmaps/{id}", 99L)
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("ROADMAP_NOT_FOUND"));
    }

    @Test
    @DisplayName("다른 회원의 로드맵 삭제 시 403을 반환한다")
    void delete_forbidden() throws Exception {
        willThrow(new BusinessException(ErrorCode.FORBIDDEN))
                .given(roadmapUseCase).deleteRoadmap(1L, MEMBER_ID);

        mockMvc.perform(delete("/api/roadmaps/{id}", 1L)
                        .requestAttr(AuthenticatedMember.REQUEST_ATTRIBUTE_NAME, loginMember()))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("FORBIDDEN"));
    }

    private AuthenticatedMember loginMember() {
        return new AuthenticatedMember(MEMBER_ID, MemberRole.LEARNER);
    }

    private RoadmapRequest request() {
        return new RoadmapRequest(
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 2L)
        );
    }

    private RoadmapResult result(Long id) {
        return new RoadmapResult(
                id,
                MEMBER_ID,
                "백엔드 로드맵",
                "백엔드 개발 입문자를 위한 단계별 강좌 로드맵입니다.",
                List.of(1L, 2L),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
