package com.ohgiraffers.lxp.content.application.service;

import com.ohgiraffers.lxp.content.application.port.command.RegisterContentCommand;
import com.ohgiraffers.lxp.content.application.port.out.ContentRepositoryPort;
import com.ohgiraffers.lxp.content.application.port.out.CourseValidationPort;
import com.ohgiraffers.lxp.content.domain.model.entity.Content;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RegisterContentServiceTest {

    @InjectMocks
    private RegisterContentService registerContentService;

    @Mock
    private ContentRepositoryPort contentRepository;

    @Mock
    private CourseValidationPort courseValidation;

    @Test
    @DisplayName("콘텐츠 등록 성공 시 courseId, sequence, title, contentUrl이 저장된다")
    void register_success() {
        RegisterContentCommand command = new RegisterContentCommand(1L, 0, "Java 기초", "https://example.com/video");
        given(courseValidation.existsById(1L)).willReturn(true);
        given(contentRepository.save(any(Content.class)))
                .willAnswer(inv -> {
                    Content c = inv.getArgument(0);
                    return Content.restore(10L, c.getCourseId(), c.getSequence(), c.getTitle(), c.getContentUrl());
                });

        Long contentId = registerContentService.register(command);

        assertThat(contentId).isEqualTo(10L);
        ArgumentCaptor<Content> captor = ArgumentCaptor.forClass(Content.class);
        then(contentRepository).should().save(captor.capture());
        assertThat(captor.getValue().getCourseId()).isEqualTo(1L);
        assertThat(captor.getValue().getSequence()).isEqualTo(0);
        assertThat(captor.getValue().getTitle()).isEqualTo("Java 기초");
        assertThat(captor.getValue().getContentUrl()).isEqualTo("https://example.com/video");
    }

    @Test
    @DisplayName("존재하지 않는 강좌 ID로 등록 시 COURSE_NOT_FOUND 예외가 발생한다")
    void register_courseNotFound_throwsException() {
        RegisterContentCommand command = new RegisterContentCommand(99L, 0, "제목", "https://example.com/video");
        given(courseValidation.existsById(99L)).willReturn(false);

        assertThatThrownBy(() -> registerContentService.register(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.COURSE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("잘못된 입력값으로 등록 시 INVALID_INPUT 예외가 발생한다")
    void register_invalidInput_throwsException() {
        RegisterContentCommand command = new RegisterContentCommand(1L, 0, " ", "https://example.com/video");
        given(courseValidation.existsById(1L)).willReturn(true);

        assertThatThrownBy(() -> registerContentService.register(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_INPUT.getMessage());
    }
}
