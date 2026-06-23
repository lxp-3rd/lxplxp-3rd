package com.ohgiraffers.lxp.qna.domain.model.entity;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QuestionTest {

    @Test
    @DisplayName("질문 생성 시 상태는 PUBLISHED가 기본값이다")
    void createQuestionWithDefaultPublishedStatus() {
        Question question = Question.create(1L, 1L, "질문", "질문 내용");

        assertThat(question.getCourseId()).isEqualTo(1L);
        assertThat(question.getMemberId()).isEqualTo(1L);
        assertThat(question.getTitle()).isEqualTo("질문");
        assertThat(question.getContent()).isEqualTo("질문 내용");
        assertThat(question.getStatus()).isEqualTo(QuestionStatus.PUBLISHED);
    }

    @Test
    @DisplayName("질문 생성 파라미터는 null일 수 없다")
    void createQuestionWithNullParameterThrowsException() {
        assertInvalidInput(() -> Question.create(null, 1L, "질문", "질문 내용"));
        assertInvalidInput(() -> Question.create(1L, null, "질문", "질문 내용"));
        assertInvalidInput(() -> Question.create(1L, 1L, null, "질문 내용"));
        assertInvalidInput(() -> Question.create(1L, 1L, "질문", null));
    }

    @Test
    @DisplayName("질문 제목은 blank일 수 없고 2자 이상 200자 이하여야 한다")
    void createQuestionWithInvalidTitleThrowsException() {
        assertInvalidInput(() -> Question.create(1L, 1L, "", "질문 내용"));
        assertInvalidInput(() -> Question.create(1L, 1L, " ", "질문 내용"));
        assertInvalidInput(() -> Question.create(1L, 1L, "질", "질문 내용"));
        assertInvalidInput(() -> Question.create(1L, 1L, "가".repeat(201), "질문 내용"));
    }

    @Test
    @DisplayName("질문 내용은 blank일 수 없고 500자 이하여야 한다")
    void createQuestionWithInvalidContentThrowsException() {
        assertInvalidInput(() -> Question.create(1L, 1L, "질문", ""));
        assertInvalidInput(() -> Question.create(1L, 1L, "질문", " "));
        assertInvalidInput(() -> Question.create(1L, 1L, "질문", "가".repeat(501)));
    }

    private void assertInvalidInput(Runnable executable) {
        assertThatThrownBy(executable::run)
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INVALID_INPUT);
    }
}
