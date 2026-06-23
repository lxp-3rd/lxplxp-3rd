package com.ohgiraffers.lxp.qna.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.qna.application.dto.QuestionResult;
import com.ohgiraffers.lxp.qna.application.port.command.CreateQuestionCommand;
import com.ohgiraffers.lxp.qna.application.port.command.UpdateQuestionCommand;
import com.ohgiraffers.lxp.qna.application.port.out.CourseQueryPort;
import com.ohgiraffers.lxp.qna.application.port.out.EnrollmentQueryPort;
import com.ohgiraffers.lxp.qna.application.port.out.QuestionRepositoryPort;
import com.ohgiraffers.lxp.qna.domain.model.entity.Question;
import com.ohgiraffers.lxp.qna.domain.model.entity.QuestionStatus;

@ExtendWith(MockitoExtension.class)
@DisplayName("QuestionService 단위 테스트")
class QuestionServiceTest {

    private static final long COURSE_ID = 1L;
    private static final long MEMBER_ID = 2L;
    private static final long QUESTION_ID = 3L;

    @Mock
    private QuestionRepositoryPort questionRepositoryPort;
    @Mock
    private CourseQueryPort courseQueryPort;
    @Mock
    private EnrollmentQueryPort enrollmentQueryPort;

    @InjectMocks
    private QuestionService questionService;

    // ─── createQuestion ────────────────────────────────────────────────────

    @Test
    @DisplayName("수강 중인 수강생이 질문 생성 시 저장하고 결과를 반환한다")
    void createQuestion_success() {
        given(courseQueryPort.existsCourse(COURSE_ID)).willReturn(true);
        given(enrollmentQueryPort.isEnrolled(COURSE_ID, MEMBER_ID)).willReturn(true);
        Question saved = savedQuestion(QUESTION_ID);
        given(questionRepositoryPort.save(any(Question.class))).willReturn(saved);

        QuestionResult result = questionService.createQuestion(
                new CreateQuestionCommand(COURSE_ID, MEMBER_ID, "제목입니다", "내용입니다"));

        assertThat(result.id()).isEqualTo(QUESTION_ID);
        assertThat(result.courseId()).isEqualTo(COURSE_ID);
        assertThat(result.status()).isEqualTo(QuestionStatus.PUBLISHED);
        verify(questionRepositoryPort).save(any(Question.class));
    }

    @Test
    @DisplayName("존재하지 않는 강좌에 질문 생성 시 COURSE_NOT_FOUND — 저장하지 않는다")
    void createQuestion_courseNotFound() {
        given(courseQueryPort.existsCourse(COURSE_ID)).willReturn(false);

        assertThatThrownBy(() -> questionService.createQuestion(
                new CreateQuestionCommand(COURSE_ID, MEMBER_ID, "제목입니다", "내용입니다")))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.COURSE_NOT_FOUND);

        verify(questionRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("수강하지 않은 회원이 질문 생성 시 ENROLLMENT_NOT_FOUND — 저장하지 않는다")
    void createQuestion_notEnrolled() {
        given(courseQueryPort.existsCourse(COURSE_ID)).willReturn(true);
        given(enrollmentQueryPort.isEnrolled(COURSE_ID, MEMBER_ID)).willReturn(false);

        assertThatThrownBy(() -> questionService.createQuestion(
                new CreateQuestionCommand(COURSE_ID, MEMBER_ID, "제목입니다", "내용입니다")))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.ENROLLMENT_NOT_FOUND);

        verify(questionRepositoryPort, never()).save(any());
    }

    // ─── getQuestions ──────────────────────────────────────────────────────

    @Test
    @DisplayName("강좌 질문 목록 조회 시 해당 강좌의 질문 리스트를 반환한다")
    void getQuestions_success() {
        given(courseQueryPort.existsCourse(COURSE_ID)).willReturn(true);
        given(questionRepositoryPort.findAllByCourseId(COURSE_ID))
                .willReturn(List.of(savedQuestion(1L), savedQuestion(2L)));

        List<QuestionResult> results = questionService.getQuestions(COURSE_ID);

        assertThat(results).hasSize(2);
        verify(questionRepositoryPort).findAllByCourseId(COURSE_ID);
    }

    @Test
    @DisplayName("존재하지 않는 강좌 목록 조회 시 COURSE_NOT_FOUND")
    void getQuestions_courseNotFound() {
        given(courseQueryPort.existsCourse(COURSE_ID)).willReturn(false);

        assertThatThrownBy(() -> questionService.getQuestions(COURSE_ID))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.COURSE_NOT_FOUND);
    }

    // ─── getQuestion ───────────────────────────────────────────────────────

    @Test
    @DisplayName("존재하는 질문 단건 조회 시 결과를 반환한다")
    void getQuestion_success() {
        given(questionRepositoryPort.findById(QUESTION_ID))
                .willReturn(Optional.of(savedQuestion(QUESTION_ID)));

        QuestionResult result = questionService.getQuestion(QUESTION_ID);

        assertThat(result.id()).isEqualTo(QUESTION_ID);
    }

    @Test
    @DisplayName("존재하지 않는 질문 단건 조회 시 QUESTION_NOT_FOUND")
    void getQuestion_notFound() {
        given(questionRepositoryPort.findById(QUESTION_ID)).willReturn(Optional.empty());

        assertThatThrownBy(() -> questionService.getQuestion(QUESTION_ID))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.QUESTION_NOT_FOUND);
    }

    // ─── updateQuestion ────────────────────────────────────────────────────

    @Test
    @DisplayName("작성자가 질문 수정 시 변경된 결과를 반환한다")
    void updateQuestion_success() {
        Question original = savedQuestion(QUESTION_ID);
        given(questionRepositoryPort.findById(QUESTION_ID)).willReturn(Optional.of(original));
        Question updated = questionWithContent(QUESTION_ID, "수정된 제목", "수정된 내용");
        given(questionRepositoryPort.save(any(Question.class))).willReturn(updated);

        QuestionResult result = questionService.updateQuestion(
                new UpdateQuestionCommand(QUESTION_ID, MEMBER_ID, "수정된 제목", "수정된 내용"));

        assertThat(result.title()).isEqualTo("수정된 제목");
        assertThat(result.content()).isEqualTo("수정된 내용");
        verify(questionRepositoryPort).save(any(Question.class));
    }

    @Test
    @DisplayName("존재하지 않는 질문 수정 시 QUESTION_NOT_FOUND — 저장하지 않는다")
    void updateQuestion_notFound() {
        given(questionRepositoryPort.findById(QUESTION_ID)).willReturn(Optional.empty());

        assertThatThrownBy(() -> questionService.updateQuestion(
                new UpdateQuestionCommand(QUESTION_ID, MEMBER_ID, "수정 제목", "수정 내용")))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.QUESTION_NOT_FOUND);

        verify(questionRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("작성자가 아닌 회원이 수정 시 예외 — 저장하지 않는다")
    void updateQuestion_wrongWriter() {
        given(questionRepositoryPort.findById(QUESTION_ID))
                .willReturn(Optional.of(savedQuestion(QUESTION_ID)));

        long otherId = MEMBER_ID + 99L;
        assertThatThrownBy(() -> questionService.updateQuestion(
                new UpdateQuestionCommand(QUESTION_ID, otherId, "수정 제목", "수정 내용")))
                .isInstanceOf(BusinessException.class);

        verify(questionRepositoryPort, never()).save(any());
    }

    // ─── deleteQuestion ────────────────────────────────────────────────────

    @Test
    @DisplayName("작성자가 질문 삭제 시 deleteById를 호출한다")
    void deleteQuestion_success() {
        given(questionRepositoryPort.findById(QUESTION_ID))
                .willReturn(Optional.of(savedQuestion(QUESTION_ID)));

        questionService.deleteQuestion(QUESTION_ID, MEMBER_ID);

        verify(questionRepositoryPort).deleteById(QUESTION_ID);
    }

    @Test
    @DisplayName("존재하지 않는 질문 삭제 시 QUESTION_NOT_FOUND — deleteById 호출하지 않는다")
    void deleteQuestion_notFound() {
        given(questionRepositoryPort.findById(QUESTION_ID)).willReturn(Optional.empty());

        assertThatThrownBy(() -> questionService.deleteQuestion(QUESTION_ID, MEMBER_ID))
                .isInstanceOf(BusinessException.class)
                .extracting(e -> ((BusinessException) e).getErrorCode())
                .isEqualTo(ErrorCode.QUESTION_NOT_FOUND);

        verify(questionRepositoryPort, never()).deleteById(any());
    }

    @Test
    @DisplayName("작성자가 아닌 회원이 삭제 시 예외 — deleteById 호출하지 않는다")
    void deleteQuestion_wrongWriter() {
        given(questionRepositoryPort.findById(QUESTION_ID))
                .willReturn(Optional.of(savedQuestion(QUESTION_ID)));

        long otherId = MEMBER_ID + 99L;
        assertThatThrownBy(() -> questionService.deleteQuestion(QUESTION_ID, otherId))
                .isInstanceOf(BusinessException.class);

        verify(questionRepositoryPort, never()).deleteById(any());
    }

    // ─── helpers ──────────────────────────────────────────────────────────

    private Question savedQuestion(Long id) {
        return new Question(id, COURSE_ID, MEMBER_ID, "제목입니다", "내용입니다",
                QuestionStatus.PUBLISHED, LocalDateTime.now(), LocalDateTime.now(), null);
    }

    private Question questionWithContent(Long id, String title, String content) {
        return new Question(id, COURSE_ID, MEMBER_ID, title, content,
                QuestionStatus.PUBLISHED, LocalDateTime.now(), LocalDateTime.now(), null);
    }
}
