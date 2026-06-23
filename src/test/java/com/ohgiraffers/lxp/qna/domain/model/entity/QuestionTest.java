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

    @Test
    @DisplayName("작성자가 질문을 수정하면 변경된 내용이 반영된다")
    void updateQuestionByWriter() {
        Question question = Question.create(1L, 1L, "원본 제목", "원본 내용");

        Question updated = question.update(1L, "수정된 제목", "수정된 내용");

        assertThat(updated.getTitle()).isEqualTo("수정된 제목");
        assertThat(updated.getContent()).isEqualTo("수정된 내용");
        assertThat(updated.getMemberId()).isEqualTo(1L);
        assertThat(updated.getStatus()).isEqualTo(QuestionStatus.PUBLISHED);
    }

    @Test
    @DisplayName("작성자가 아닌 사람이 수정 시 예외가 발생한다")
    void updateQuestionByOtherMemberThrowsException() {
        Question question = Question.create(1L, 1L, "제목", "내용");

        assertThatThrownBy(() -> question.update(2L, "수정 제목", "수정 내용"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.QUESTION_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("수정 시 제목이 유효하지 않으면 예외가 발생한다")
    void updateQuestionWithInvalidTitleThrowsException() {
        Question question = Question.create(1L, 1L, "제목", "내용");

        assertInvalidInput(() -> question.update(1L, "짧", "수정 내용"));
        assertInvalidInput(() -> question.update(1L, "가".repeat(201), "수정 내용"));
        assertInvalidInput(() -> question.update(1L, "", "수정 내용"));
    }

    @Test
    @DisplayName("수정 시 내용이 유효하지 않으면 예외가 발생한다")
    void updateQuestionWithInvalidContentThrowsException() {
        Question question = Question.create(1L, 1L, "제목", "내용");

        assertInvalidInput(() -> question.update(1L, "제목", ""));
        assertInvalidInput(() -> question.update(1L, "제목", "가".repeat(501)));
    }

    @Test
    @DisplayName("제목 경계값 — 2자와 200자는 유효하다")
    void titleBoundaryValues() {
        Question twoChar = Question.create(1L, 1L, "질문", "내용");
        Question twoHundredChar = Question.create(1L, 1L, "가".repeat(200), "내용");

        assertThat(twoChar.getTitle()).hasSize(2);
        assertThat(twoHundredChar.getTitle()).hasSize(200);
    }

    @Test
    @DisplayName("내용 경계값 — 500자는 유효하다")
    void contentBoundaryValue() {
        Question question = Question.create(1L, 1L, "제목", "가".repeat(500));

        assertThat(question.getContent()).hasSize(500);
    }

    private void assertInvalidInput(Runnable executable) {
        assertThatThrownBy(executable::run)
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_INPUT.getMessage());
    }
}
