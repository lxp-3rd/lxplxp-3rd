package com.ohgiraffers.lxp.qna.application.port.out;

import com.ohgiraffers.lxp.qna.domain.model.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepositoryPort {

    Question save(Question question);

    List<Question> findAllByCourseId(Long courseId);

    List<Question> findAll();

    Optional<Question> findById(Long questionId);

    void deleteById(Long questionId);

    Question saveAnswer(Question question);
}
