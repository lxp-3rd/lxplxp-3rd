package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseLikeJpaRepository extends JpaRepository<CourseLikeJpaEntity, Long> {
    boolean existsByCourseIdAndLearnerId(Long courseId, Long learnerId);
    Optional<CourseLikeJpaEntity> findByCourseIdAndLearnerId(Long courseId, Long learnerId);
    long countByCourseId(Long courseId);
}
