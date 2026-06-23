package com.ohgiraffers.lxp.qna.presentation.dto;

import com.ohgiraffers.lxp.qna.application.port.command.CreateQuestionCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateQuestionRequest(
        @NotNull Long courseId,
        @NotNull Long memberId,
        @NotBlank @Size(min = 2, max = 200) String title,
        @NotBlank @Size(max = 500) String content
) {

    public CreateQuestionCommand toCommand() {
        return new CreateQuestionCommand(courseId, memberId, title, content);
    }
}
