package com.ohgiraffers.lxp.qna.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.qna.application.dto.QuestionResult;
import com.ohgiraffers.lxp.qna.application.port.command.CreateQuestionCommand;
import com.ohgiraffers.lxp.qna.application.port.command.UpdateQuestionCommand;
import com.ohgiraffers.lxp.qna.application.port.in.QuestionUseCase;
import com.ohgiraffers.lxp.qna.application.port.out.CourseQueryPort;
import com.ohgiraffers.lxp.qna.application.port.out.EnrollmentQueryPort;
import com.ohgiraffers.lxp.qna.application.port.out.QuestionRepositoryPort;
import com.ohgiraffers.lxp.qna.domain.model.entity.Question;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService implements QuestionUseCase {

	private final QuestionRepositoryPort questionRepositoryPort;
	private final CourseQueryPort courseQueryPort;
	private final EnrollmentQueryPort enrollmentQueryPort;

	@Override
	@Transactional
	public QuestionResult createQuestion(CreateQuestionCommand command) {
		validateCourse(command.courseId());
		validateEnrolled(command.courseId(), command.memberId());

		Question question = Question.create(
			command.courseId(),
			command.memberId(),
			command.title(),
			command.content()
		);

		return QuestionResult.from(questionRepositoryPort.save(question));
	}

	@Override
	public List<QuestionResult> getQuestions(Long courseId) {
		validateCourse(courseId);
		return questionRepositoryPort.findAllByCourseId(courseId)
			.stream()
			.map(QuestionResult::from)
			.toList();
	}

	@Override
	public QuestionResult getQuestion(Long questionId) {
		return QuestionResult.from(getQuestionOrThrow(questionId));
	}

	@Override
	@Transactional
	public QuestionResult updateQuestion(UpdateQuestionCommand command) {
		Question question = getQuestionOrThrow(command.questionId());

		Question updatedQuestion = question.update(command.memberId(), command.title(), command.content());
		return QuestionResult.from(questionRepositoryPort.save(updatedQuestion));
	}

	@Override
	@Transactional
	public void deleteQuestion(Long questionId, Long memberId) {
		Question question = getQuestionOrThrow(questionId);
		question.validateWriter(memberId);

		questionRepositoryPort.deleteById(questionId);
	}

	private Question getQuestionOrThrow(Long questionId) {
		return questionRepositoryPort.findById(questionId)
			.orElseThrow(() -> new BusinessException(ErrorCode.QUESTION_NOT_FOUND));
	}

	private void validateCourse(Long courseId) {
		if (!courseQueryPort.existsCourse(courseId)) {
			throw new BusinessException(ErrorCode.COURSE_NOT_FOUND);
		}
	}

	private void validateEnrolled(Long courseId, Long memberId) {
		if (!enrollmentQueryPort.isEnrolled(courseId, memberId)) {
			throw new BusinessException(ErrorCode.ENROLLMENT_NOT_FOUND);
		}
	}
}
