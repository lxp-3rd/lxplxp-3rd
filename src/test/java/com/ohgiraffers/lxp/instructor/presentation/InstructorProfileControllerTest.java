package com.ohgiraffers.lxp.instructor.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.in.GetInstructorProfileUseCase;
import com.ohgiraffers.lxp.instructor.application.port.in.RegisterInstructorProfileUseCase;
import com.ohgiraffers.lxp.instructor.application.port.in.RegisterInstructorProfileUseCase.RegisterInstructorProfileCommand;
import com.ohgiraffers.lxp.instructor.application.port.in.UpdateInstructorProfileUseCase;
import com.ohgiraffers.lxp.instructor.application.port.in.UpdateInstructorProfileUseCase.UpdateInstructorProfileCommand;
import com.ohgiraffers.lxp.instructor.domain.InstructorProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.mockito.ArgumentCaptor;

@WebMvcTest(InstructorProfileController.class)
class InstructorProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegisterInstructorProfileUseCase registerInstructorProfileUseCase;

    @MockitoBean
    private UpdateInstructorProfileUseCase updateInstructorProfileUseCase;

    @MockitoBean
    private GetInstructorProfileUseCase getInstructorProfileUseCase;

    @Test
    @DisplayName("프로필 등록 성공 시 201을 반환한다")
    void register_success_returns201() throws Exception {
        RegisterInstructorProfileRequest request = new RegisterInstructorProfileRequest(
                "https://example.com/image.jpg", "10년 경력의 Java 개발자입니다."
        );
        willDoNothing().given(registerInstructorProfileUseCase)
                .register(any(RegisterInstructorProfileCommand.class));

        mockMvc.perform(post("/api/instructors/1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("프로필 등록 시 필수 필드 누락이면 400을 반환한다")
    void register_missingField_returns400() throws Exception {
        RegisterInstructorProfileRequest request = new RegisterInstructorProfileRequest(
                "", "자기소개"
        );

        mockMvc.perform(post("/api/instructors/1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 강사 ID로 등록 시 404를 반환한다")
    void register_instructorNotFound_returns404() throws Exception {
        RegisterInstructorProfileRequest request = new RegisterInstructorProfileRequest(
                "https://example.com/image.jpg", "자기소개"
        );
        willThrow(new BusinessException(ErrorCode.INSTRUCTOR_NOT_FOUND))
                .given(registerInstructorProfileUseCase)
                .register(any(RegisterInstructorProfileCommand.class));

        mockMvc.perform(post("/api/instructors/99/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("이미 프로필이 존재하면 409를 반환한다")
    void register_profileAlreadyExists_returns409() throws Exception {
        RegisterInstructorProfileRequest request = new RegisterInstructorProfileRequest(
                "https://example.com/image.jpg", "자기소개"
        );
        willThrow(new BusinessException(ErrorCode.INSTRUCTOR_PROFILE_ALREADY_EXISTS))
                .given(registerInstructorProfileUseCase)
                .register(any(RegisterInstructorProfileCommand.class));

        mockMvc.perform(post("/api/instructors/1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("프로필 수정 성공 시 200을 반환한다")
    void update_success_returns200() throws Exception {
        UpdateInstructorProfileRequest request = new UpdateInstructorProfileRequest(
                "https://example.com/new.jpg", "새로운 자기소개"
        );
        willDoNothing().given(updateInstructorProfileUseCase)
                .update(any(UpdateInstructorProfileCommand.class));

        mockMvc.perform(put("/api/instructors/1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("프로필 수정 시 필수 필드 누락이면 400을 반환한다")
    void update_missingField_returns400() throws Exception {
        UpdateInstructorProfileRequest request = new UpdateInstructorProfileRequest(
                "https://example.com/new.jpg", ""
        );

        mockMvc.perform(put("/api/instructors/1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("등록 시 PathVariable id가 Command의 instructorId로 전달된다")
    void register_pathVariableBindsToInstructorId() throws Exception {
        RegisterInstructorProfileRequest request = new RegisterInstructorProfileRequest(
                "https://example.com/image.jpg", "자기소개"
        );
        willDoNothing().given(registerInstructorProfileUseCase)
                .register(any(RegisterInstructorProfileCommand.class));

        mockMvc.perform(post("/api/instructors/42/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        ArgumentCaptor<RegisterInstructorProfileCommand> captor = forClass(RegisterInstructorProfileCommand.class);
        verify(registerInstructorProfileUseCase).register(captor.capture());
        assertThat(captor.getValue().instructorId()).isEqualTo(42L);
    }

    @Test
    @DisplayName("수정 시 PathVariable id가 Command의 instructorId로 전달된다")
    void update_pathVariableBindsToInstructorId() throws Exception {
        UpdateInstructorProfileRequest request = new UpdateInstructorProfileRequest(
                "https://example.com/new.jpg", "새로운 자기소개"
        );
        willDoNothing().given(updateInstructorProfileUseCase)
                .update(any(UpdateInstructorProfileCommand.class));

        mockMvc.perform(put("/api/instructors/42/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        ArgumentCaptor<UpdateInstructorProfileCommand> captor = forClass(UpdateInstructorProfileCommand.class);
        verify(updateInstructorProfileUseCase).update(captor.capture());
        assertThat(captor.getValue().instructorId()).isEqualTo(42L);
    }

    @Test
    @DisplayName("프로필이 없는 상태에서 수정 시 404를 반환한다")
    void update_profileNotFound_returns404() throws Exception {
        UpdateInstructorProfileRequest request = new UpdateInstructorProfileRequest(
                "https://example.com/new.jpg", "새로운 자기소개"
        );
        willThrow(new BusinessException(ErrorCode.INSTRUCTOR_PROFILE_NOT_FOUND))
                .given(updateInstructorProfileUseCase)
                .update(any(UpdateInstructorProfileCommand.class));

        mockMvc.perform(put("/api/instructors/1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("프로필 조회 성공 시 200과 응답 바디를 반환한다")
    void get_success_returns200WithBody() throws Exception {
        InstructorProfile profile = InstructorProfile.create(
                1L, "https://example.com/image.jpg", "10년 경력의 Java 개발자입니다."
        );
        given(getInstructorProfileUseCase.get(1L)).willReturn(profile);

        mockMvc.perform(get("/api/instructors/1/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instructorId").value(1))
                .andExpect(jsonPath("$.profileImageUrl").value("https://example.com/image.jpg"))
                .andExpect(jsonPath("$.bio").value("10년 경력의 Java 개발자입니다."));
    }

    @Test
    @DisplayName("프로필 조회 시 프로필이 없으면 404를 반환한다")
    void get_profileNotFound_returns404() throws Exception {
        willThrow(new BusinessException(ErrorCode.INSTRUCTOR_PROFILE_NOT_FOUND))
                .given(getInstructorProfileUseCase)
                .get(99L);

        mockMvc.perform(get("/api/instructors/99/profile"))
                .andExpect(status().isNotFound());
    }
}
