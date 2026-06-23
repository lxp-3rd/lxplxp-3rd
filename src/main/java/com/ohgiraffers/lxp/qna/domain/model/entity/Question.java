package com.ohgiraffers.lxp.qna.domain.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Question {

	private static final int MINIMUM_TITLE_LENGTH = 2;
	private static final int MAXIMUM_TITLE_LENGTH = 200;
	private static final int MAXIMUM_CONTENT_LENGTH = 500;

	private final Long id;
	private final Long courseId;
	private final Long memberId;
	private final String title;
	private final String content;
	private final QuestionStatus status;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;
	private final LocalDateTime deletedAt;

	public static Question create(Long courseId, Long memberId, String title, String content) {
		Objects.requireNonNull(memberId);
		Objects.requireNonNull(courseId);

		validateTitle(Objects.requireNonNull(title));
		validateContent(Objects.requireNonNull(content));
		return new Question(null, courseId, memberId, title, content, QuestionStatus.PUBLISHED, null, null, null);
	}

	public Question update(Long writerId, String title, String content) {
		validateWriter(writerId);

		validateTitle(Objects.requireNonNull(title));
		validateContent(Objects.requireNonNull(content));
		return new Question(id, courseId, memberId, title, content, status, createdAt, updatedAt, deletedAt);
	}

	public void validateWriter(Long writerId) {
		if (!memberId.equals(writerId)) {
			throw new BusinessException(ErrorCode.QUESTION_NOT_FOUND);
		}
	}

	private static void validateTitle(String title) {
		if (title.isBlank() || title.length() < MINIMUM_TITLE_LENGTH || title.length() > MAXIMUM_TITLE_LENGTH) {
			throw new BusinessException(ErrorCode.INVALID_INPUT);
		}
	}

	private static void validateContent(String content) {
		if (content.isBlank() || content.length() > MAXIMUM_CONTENT_LENGTH) {
			throw new BusinessException(ErrorCode.INVALID_INPUT);
		}
	}
}
