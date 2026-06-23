package com.ohgiraffers.lxp.content.application.service;

import com.ohgiraffers.lxp.content.application.port.command.UpdateContentCommand;
import com.ohgiraffers.lxp.content.application.port.out.ContentRepositoryPort;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UpdateContentServiceTest {

    @InjectMocks
    private UpdateContentService updateContentService;

    @Mock
    private ContentRepositoryPort contentRepository;

    @Test
    @DisplayName("콘텐츠 수정 성공 시 title과 contentUrl이 저장된다")
    void update_success() {
        Content existing = Content.restore(10L, 1L, 0, "기존 제목", "https://example.com/old");
        given(contentRepository.findById(10L)).willReturn(Optional.of(existing));
        given(contentRepository.save(any(Content.class))).willAnswer(inv -> inv.getArgument(0));
        UpdateContentCommand command = new UpdateContentCommand(1L, 10L, "새 제목", "https://example.com/new");

        updateContentService.update(command);

        ArgumentCaptor<Content> captor = ArgumentCaptor.forClass(Content.class);
        then(contentRepository).should().save(captor.capture());
        assertThat(captor.getValue().getTitle()).isEqualTo("새 제목");
        assertThat(captor.getValue().getContentUrl()).isEqualTo("https://example.com/new");
    }

    @Test
    @DisplayName("존재하지 않는 콘텐츠 수정 시 CONTENT_NOT_FOUND 예외가 발생한다")
    void update_contentNotFound_throwsException() {
        given(contentRepository.findById(99L)).willReturn(Optional.empty());
        UpdateContentCommand command = new UpdateContentCommand(1L, 99L, "제목", "https://example.com/video");

        assertThatThrownBy(() -> updateContentService.update(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.CONTENT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("잘못된 입력값으로 수정 시 INVALID_INPUT 예외가 발생한다")
    void update_invalidInput_throwsException() {
        Content existing = Content.restore(10L, 1L, 0, "기존 제목", "https://example.com/old");
        given(contentRepository.findById(10L)).willReturn(Optional.of(existing));
        UpdateContentCommand command = new UpdateContentCommand(1L, 10L, " ", "https://example.com/new");

        assertThatThrownBy(() -> updateContentService.update(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_INPUT.getMessage());
    }
}
