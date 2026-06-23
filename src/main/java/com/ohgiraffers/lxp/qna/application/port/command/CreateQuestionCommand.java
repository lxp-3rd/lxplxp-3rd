package com.ohgiraffers.lxp.qna.application.port.command;

public record CreateQuestionCommand(
        Long courseId,
        Long memberId,
        String title,
        String content
) {
}
