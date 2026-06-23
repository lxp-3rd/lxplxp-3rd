package com.ohgiraffers.lxp.qna.application.port.command;

public record UpdateQuestionCommand(
        Long questionId,
        Long memberId,
        String title,
        String content
) {
}
