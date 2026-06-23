package com.ohgiraffers.lxp.global.upload.application;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.global.upload.application.dto.ImageUploadResult;
import com.ohgiraffers.lxp.global.upload.application.port.command.UploadImageCommand;
import com.ohgiraffers.lxp.global.upload.application.port.out.ImageStoragePort;
import com.ohgiraffers.lxp.global.upload.application.service.ImageUploadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("이미지 업로드 서비스 테스트")
class ImageUploadServiceTest {

    @Mock
    private ImageStoragePort imageStoragePort;

    @InjectMocks
    private ImageUploadService imageUploadService;

    @Test
    @DisplayName("유효한 이미지를 업로드하면 /images/{uuid}.확장자 경로를 반환한다")
    void upload_success() {
        UploadImageCommand command = new UploadImageCommand("content".getBytes(), "test.jpg");
        given(imageStoragePort.save(any(), any())).willReturn("uuid.jpg");

        ImageUploadResult result = imageUploadService.upload(command);

        assertThat(result.imageUrl()).matches("/images/[a-f0-9\\-]{36}\\.jpg");
        verify(imageStoragePort).save(any(), any());
    }

    @Test
    @DisplayName("대문자 확장자(.JPG)는 소문자로 정규화되어 저장된다")
    void upload_uppercaseExtension_normalized() {
        UploadImageCommand command = new UploadImageCommand("content".getBytes(), "photo.JPG");
        given(imageStoragePort.save(any(), any())).willReturn("uuid.jpg");

        ImageUploadResult result = imageUploadService.upload(command);

        assertThat(result.imageUrl()).endsWith(".jpg");
    }

    @Test
    @DisplayName("파일 데이터가 비어있으면 IMAGE_INVALID_FILE 예외가 발생한다")
    void upload_emptyFile_throwsException() {
        UploadImageCommand command = new UploadImageCommand(new byte[0], "test.jpg");

        assertThatThrownBy(() -> imageUploadService.upload(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.IMAGE_INVALID_FILE.getMessage());
    }

    @Test
    @DisplayName("null 파일 데이터면 IMAGE_INVALID_FILE 예외가 발생한다")
    void upload_nullFileData_throwsException() {
        UploadImageCommand command = new UploadImageCommand(null, "test.jpg");

        assertThatThrownBy(() -> imageUploadService.upload(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.IMAGE_INVALID_FILE.getMessage());
    }

    @Test
    @DisplayName("허용되지 않는 확장자면 IMAGE_INVALID_TYPE 예외가 발생한다")
    void upload_invalidExtension_throwsException() {
        UploadImageCommand command = new UploadImageCommand("content".getBytes(), "test.exe");

        assertThatThrownBy(() -> imageUploadService.upload(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.IMAGE_INVALID_TYPE.getMessage());
    }

    @Test
    @DisplayName("확장자가 없는 파일명이면 IMAGE_INVALID_TYPE 예외가 발생한다")
    void upload_noExtension_throwsException() {
        UploadImageCommand command = new UploadImageCommand("content".getBytes(), "testfile");

        assertThatThrownBy(() -> imageUploadService.upload(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.IMAGE_INVALID_TYPE.getMessage());
    }
}
