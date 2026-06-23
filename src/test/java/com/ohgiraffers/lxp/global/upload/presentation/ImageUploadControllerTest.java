package com.ohgiraffers.lxp.global.upload.presentation;

import com.ohgiraffers.lxp.auth.application.dto.AuthenticatedMember;
import com.ohgiraffers.lxp.auth.application.port.out.TokenValidatePort;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.global.upload.application.dto.ImageUploadResult;
import com.ohgiraffers.lxp.global.upload.application.port.in.UploadImageUseCase;
import com.ohgiraffers.lxp.global.upload.presentation.web.ImageUploadController;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageUploadController.class)
@DisplayName("이미지 업로드 컨트롤러 테스트")
class ImageUploadControllerTest {

    private static final String INSTRUCTOR_TOKEN = "instructor-token";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UploadImageUseCase uploadImageUseCase;

    @MockitoBean
    private TokenValidatePort tokenValidatePort;

    @BeforeEach
    void setUp() {
        given(tokenValidatePort.validateAccessToken(INSTRUCTOR_TOKEN))
                .willReturn(new AuthenticatedMember(1L, MemberRole.INSTRUCTOR));
    }

    @Test
    @DisplayName("이미지 업로드 성공 시 imageUrl을 반환한다")
    void upload_success() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "content".getBytes()
        );
        given(uploadImageUseCase.upload(any())).willReturn(new ImageUploadResult("/images/uuid.jpg"));

        mockMvc.perform(multipart("/api/images")
                        .file(file)
                        .header(HttpHeaders.AUTHORIZATION, bearerInstructorToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imageUrl").value("/images/uuid.jpg"));
    }

    @Test
    @DisplayName("빈 파일 업로드 시 400 응답을 반환한다")
    void upload_emptyFile_returns400() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", new byte[0]
        );
        given(uploadImageUseCase.upload(any()))
                .willThrow(new BusinessException(ErrorCode.IMAGE_INVALID_FILE));

        mockMvc.perform(multipart("/api/images")
                        .file(file)
                        .header(HttpHeaders.AUTHORIZATION, bearerInstructorToken()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("IMAGE_INVALID_FILE"));
    }

    @Test
    @DisplayName("허용되지 않는 확장자 업로드 시 400 응답을 반환한다")
    void upload_invalidType_returns400() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image", "test.exe", "application/octet-stream", "content".getBytes()
        );
        given(uploadImageUseCase.upload(any()))
                .willThrow(new BusinessException(ErrorCode.IMAGE_INVALID_TYPE));

        mockMvc.perform(multipart("/api/images")
                        .file(file)
                        .header(HttpHeaders.AUTHORIZATION, bearerInstructorToken()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("IMAGE_INVALID_TYPE"));
    }

    private String bearerInstructorToken() {
        return "Bearer " + INSTRUCTOR_TOKEN;
    }
}
