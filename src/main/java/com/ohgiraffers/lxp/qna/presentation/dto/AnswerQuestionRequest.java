package com.ohgiraffers.lxp.qna.presentation.dto;

import com.ohgiraffers.lxp.qna.application.port.command.AnswerQuestionCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AnswerQuestionRequest(
        @NotNull Long instructorId,
        @NotBlank @Size(max = 2000) String content
) {

    public AnswerQuestionCommand toCommand(Long questionId) {
        return new AnswerQuestionCommand(questionId, instructorId, content);
    }
}
