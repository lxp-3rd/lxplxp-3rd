package com.ohgiraffers.lxp.course.presentation.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.course.application.port.command.ChangeCourseStatusCommand;
import com.ohgiraffers.lxp.course.application.port.command.RegisterCourseCommand;
import com.ohgiraffers.lxp.course.application.port.command.UpdateCourseCommand;
import com.ohgiraffers.lxp.course.application.port.in.ChangeCourseStatusUseCase;
import com.ohgiraffers.lxp.course.application.port.in.DeleteCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.in.RegisterCourseUseCase;
import com.ohgiraffers.lxp.course.application.port.in.UpdateCourseUseCase;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.entity.HiddenBy;
import com.ohgiraffers.lxp.course.presentation.dto.ChangeCourseStatusRequest;
import com.ohgiraffers.lxp.course.presentation.dto.RegisterCourseRequest;
import com.ohgiraffers.lxp.course.presentation.dto.UpdateCourseRequest;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @MockitoBean
    private UpdateCourseUseCase updateCourseUseCase;

    @MockitoBean
    private DeleteCourseUseCase deleteCourseUseCase;

    @MockitoBean
    private ChangeCourseStatusUseCase changeCourseStatusUseCase;

    @Test
    @DisplayName("강좌 등록 성공 시 201과 courseId를 반환한다")
    void register_success_returns201() throws Exception {
        RegisterCourseRequest request = new RegisterCourseRequest(
                1L, "강좌 제목", "강좌 설명입니다.", null, List.of("소개", "실습")
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
                null, "강좌 제목", "강좌 설명입니다.", null, List.of()
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
                1L, "", "강좌 설명입니다.", null, List.of()
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
                99L, "강좌 제목", "강좌 설명입니다.", null, List.of()
        );
        willThrow(new BusinessException(ErrorCode.INSTRUCTOR_NOT_FOUND))
                .given(registerCourseUseCase).register(any(RegisterCourseCommand.class));

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("강좌 수정 성공 시 200을 반환한다")
    void update_success_returns200() throws Exception {
        UpdateCourseRequest request = new UpdateCourseRequest("새 제목", "새 설명입니다.", null);
        willDoNothing().given(updateCourseUseCase).update(any(UpdateCourseCommand.class));

        mockMvc.perform(put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("존재하지 않는 강좌 수정 시 404를 반환한다")
    void update_courseNotFound_returns404() throws Exception {
        UpdateCourseRequest request = new UpdateCourseRequest("새 제목", "새 설명입니다.", null);
        willThrow(new BusinessException(ErrorCode.COURSE_NOT_FOUND))
                .given(updateCourseUseCase).update(any(UpdateCourseCommand.class));

        mockMvc.perform(put("/api/courses/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("수정 시 title이 빈 문자열이면 400을 반환한다")
    void update_blankTitle_returns400() throws Exception {
        UpdateCourseRequest request = new UpdateCourseRequest("", "새 설명입니다.", null);

        mockMvc.perform(put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("강좌 삭제 성공 시 204를 반환한다")
    void delete_success_returns204() throws Exception {
        willDoNothing().given(deleteCourseUseCase).delete(anyLong());

        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("존재하지 않는 강좌 삭제 시 404를 반환한다")
    void delete_courseNotFound_returns404() throws Exception {
        willThrow(new BusinessException(ErrorCode.COURSE_NOT_FOUND))
                .given(deleteCourseUseCase).delete(anyLong());

        mockMvc.perform(delete("/api/courses/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("강좌 상태 변경 성공 시 200을 반환한다")
    void changeStatus_success_returns200() throws Exception {
        ChangeCourseStatusRequest request = new ChangeCourseStatusRequest(CourseStatus.HIDDEN, HiddenBy.INSTRUCTOR);
        willDoNothing().given(changeCourseStatusUseCase).changeStatus(any(ChangeCourseStatusCommand.class));

        mockMvc.perform(patch("/api/courses/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("강좌 상태 변경 시 status가 null이면 400을 반환한다")
    void changeStatus_nullStatus_returns400() throws Exception {
        ChangeCourseStatusRequest request = new ChangeCourseStatusRequest(null, HiddenBy.INSTRUCTOR);

        mockMvc.perform(patch("/api/courses/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("관리자 숨김 강좌를 강사가 공개하려 하면 403을 반환한다")
    void changeStatus_notAllowed_returns403() throws Exception {
        ChangeCourseStatusRequest request = new ChangeCourseStatusRequest(CourseStatus.PUBLIC, HiddenBy.INSTRUCTOR);
        willThrow(new BusinessException(ErrorCode.COURSE_STATUS_CHANGE_NOT_ALLOWED))
                .given(changeCourseStatusUseCase).changeStatus(any(ChangeCourseStatusCommand.class));

        mockMvc.perform(patch("/api/courses/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
}
