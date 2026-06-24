package com.ohgiraffers.lxp.qna.application.port.in;

import com.ohgiraffers.lxp.qna.application.dto.QuestionResult;
import com.ohgiraffers.lxp.qna.application.port.command.CreateQuestionCommand;
import com.ohgiraffers.lxp.qna.application.port.command.UpdateQuestionCommand;

import java.util.List;

public interface QuestionUseCase {

    QuestionResult createQuestion(CreateQuestionCommand command);

    List<QuestionResult> getQuestions();

    List<QuestionResult> getQuestions(Long courseId);

    QuestionResult getQuestion(Long questionId);

    QuestionResult updateQuestion(UpdateQuestionCommand command);

    void deleteQuestion(Long questionId, Long memberId);
}
