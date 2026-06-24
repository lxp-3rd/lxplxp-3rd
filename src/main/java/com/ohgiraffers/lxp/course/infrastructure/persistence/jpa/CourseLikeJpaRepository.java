package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseLikeJpaRepository extends JpaRepository<CourseLikeJpaEntity, Long> {
    boolean existsByCourseIdAndLearnerId(Long courseId, Long learnerId);
    Optional<CourseLikeJpaEntity> findByCourseIdAndLearnerId(Long courseId, Long learnerId);
    long countByCourseId(Long courseId);

    @Query("""
            SELECT cl.courseId AS courseId, COUNT(cl) AS count
            FROM CourseLikeJpaEntity cl
            WHERE cl.courseId IN :courseIds
            GROUP BY cl.courseId
            """)
    List<CourseLikeCount> countByCourseIds(@Param("courseIds") List<Long> courseIds);

    interface CourseLikeCount {
        Long getCourseId();

        long getCount();
    }
}
