package com.ohgiraffers.lxp.qna.presentation.dto;

import com.ohgiraffers.lxp.qna.application.dto.QuestionResult;
import com.ohgiraffers.lxp.qna.domain.model.entity.QuestionStatus;

import java.time.LocalDateTime;

public record QuestionResponse(
        Long id,
        Long courseId,
        Long memberId,
        String title,
        String content,
        QuestionStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String answer,
        Long answeredBy,
        LocalDateTime answeredAt
) {

    public static QuestionResponse from(QuestionResult result) {
        return new QuestionResponse(
                result.id(),
                result.courseId(),
                result.memberId(),
                result.title(),
                result.content(),
                result.status(),
                result.createdAt(),
                result.updatedAt(),
                result.answer(),
                result.answeredBy(),
                result.answeredAt()
        );
    }
}
