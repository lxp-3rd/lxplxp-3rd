package com.ohgiraffers.lxp.qna.application.port.in;

import com.ohgiraffers.lxp.qna.application.dto.QuestionResult;
import com.ohgiraffers.lxp.qna.application.port.command.AnswerQuestionCommand;

public interface AnswerQuestionUseCase {

    QuestionResult answerQuestion(AnswerQuestionCommand command);
}
