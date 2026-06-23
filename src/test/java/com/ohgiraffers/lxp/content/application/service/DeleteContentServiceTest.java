package com.ohgiraffers.lxp.content.application.service;

import com.ohgiraffers.lxp.content.application.port.out.ContentRepositoryPort;
import com.ohgiraffers.lxp.content.domain.model.entity.Content;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class DeleteContentServiceTest {

    @InjectMocks
    private DeleteContentService deleteContentService;

    @Mock
    private ContentRepositoryPort contentRepository;

    @Test
    @DisplayName("콘텐츠 삭제 성공 시 delete가 호출된다")
    void delete_success() {
        Content existing = Content.restore(10L, 1L, 0, "제목", "https://example.com/video");
        given(contentRepository.findById(10L)).willReturn(Optional.of(existing));

        deleteContentService.delete(10L);

        then(contentRepository).should().delete(10L);
    }

    @Test
    @DisplayName("존재하지 않는 콘텐츠 삭제 시 CONTENT_NOT_FOUND 예외가 발생한다")
    void delete_contentNotFound_throwsException() {
        given(contentRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> deleteContentService.delete(99L))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.CONTENT_NOT_FOUND.getMessage());
    }
}
