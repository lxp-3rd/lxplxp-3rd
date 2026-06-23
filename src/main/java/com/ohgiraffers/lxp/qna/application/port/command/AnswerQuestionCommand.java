package com.ohgiraffers.lxp.qna.application.port.command;

public record AnswerQuestionCommand(Long questionId, Long instructorId, String content) {
}
