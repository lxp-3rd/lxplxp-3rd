package com.ohgiraffers.lxp.qna.presentation.dto;

import com.ohgiraffers.lxp.qna.application.port.command.UpdateQuestionCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateQuestionRequest(
        @NotNull Long memberId,
        @NotBlank @Size(min = 2, max = 200) String title,
        @NotBlank @Size(max = 500) String content
) {

    public UpdateQuestionCommand toCommand(Long questionId) {
        return new UpdateQuestionCommand(questionId, memberId, title, content);
    }
}
