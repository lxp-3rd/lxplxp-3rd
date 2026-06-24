package com.ohgiraffers.lxp.course.application.port.out;

import com.ohgiraffers.lxp.course.domain.model.entity.CourseLike;

import java.util.Optional;

public interface CourseLikeRepositoryPort {
    CourseLike save(CourseLike like);
    boolean existsByCourseIdAndLearnerId(Long courseId, Long learnerId);
    Optional<CourseLike> findByCourseIdAndLearnerId(Long courseId, Long learnerId);
    void delete(Long id);
    long countByCourseId(Long courseId);
}
