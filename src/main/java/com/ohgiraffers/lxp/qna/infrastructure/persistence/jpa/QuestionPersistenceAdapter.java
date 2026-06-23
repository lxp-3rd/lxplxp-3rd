package com.ohgiraffers.lxp.qna.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.qna.application.port.out.QuestionRepositoryPort;
import com.ohgiraffers.lxp.qna.domain.model.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionPersistenceAdapter implements QuestionRepositoryPort {

    private final QuestionJpaRepository questionJpaRepository;

    @Override
	@Transactional
    public Question save(Question question) {
        if (question.getId() == null) {
            return questionJpaRepository.save(QuestionJpaEntity.from(question)).toDomain();
        }

        QuestionJpaEntity entity = questionJpaRepository.findByIdAndDeletedAtIsNull(question.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.QUESTION_NOT_FOUND));
        entity.update(question.getTitle(), question.getContent());
        return entity.toDomain();
    }

    @Override
    public List<Question> findAllByCourseId(Long courseId) {
        return questionJpaRepository.findAllByCourseIdAndDeletedAtIsNullOrderByCreatedAtDesc(courseId)
                .stream()
                .map(QuestionJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<Question> findAll() {
        return questionJpaRepository.findAllByDeletedAtIsNullOrderByCreatedAtDesc()
                .stream()
                .map(QuestionJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Question> findById(Long questionId) {
        return questionJpaRepository.findByIdAndDeletedAtIsNull(questionId)
                .map(QuestionJpaEntity::toDomain);
    }

    @Override
    public void deleteById(Long questionId) {
        QuestionJpaEntity entity = questionJpaRepository.findByIdAndDeletedAtIsNull(questionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.QUESTION_NOT_FOUND));
        entity.delete();
    }
}
