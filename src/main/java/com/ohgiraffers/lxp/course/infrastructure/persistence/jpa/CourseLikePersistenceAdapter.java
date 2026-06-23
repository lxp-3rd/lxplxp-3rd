package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.course.application.port.out.CourseLikeRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseLike;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CourseLikePersistenceAdapter implements CourseLikeRepositoryPort {

    private final CourseLikeJpaRepository jpaRepository;

    public CourseLikePersistenceAdapter(CourseLikeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public CourseLike save(CourseLike like) {
        return jpaRepository.save(CourseLikeJpaEntity.from(like)).toDomain();
    }

    @Override
    public boolean existsByCourseIdAndLearnerId(Long courseId, Long learnerId) {
        return jpaRepository.existsByCourseIdAndLearnerId(courseId, learnerId);
    }

    @Override
    public Optional<CourseLike> findByCourseIdAndLearnerId(Long courseId, Long learnerId) {
        return jpaRepository.findByCourseIdAndLearnerId(courseId, learnerId)
                .map(CourseLikeJpaEntity::toDomain);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public long countByCourseId(Long courseId) {
        return jpaRepository.countByCourseId(courseId);
    }
}
