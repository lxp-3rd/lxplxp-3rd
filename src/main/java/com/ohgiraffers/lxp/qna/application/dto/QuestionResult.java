package com.ohgiraffers.lxp.qna.application.dto;

import com.ohgiraffers.lxp.qna.domain.model.entity.Question;
import com.ohgiraffers.lxp.qna.domain.model.entity.QuestionStatus;

import java.time.LocalDateTime;

public record QuestionResult(
        Long id,
        Long courseId,
        Long memberId,
        String title,
        String content,
        QuestionStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static QuestionResult from(Question question) {
        return new QuestionResult(
                question.getId(),
                question.getCourseId(),
                question.getMemberId(),
                question.getTitle(),
                question.getContent(),
                question.getStatus(),
                question.getCreatedAt(),
                question.getUpdatedAt()
        );
    }
}
