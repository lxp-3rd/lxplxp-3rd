package com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa;

import java.util.List;

import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, Long> {

    boolean existsByMemberIdAndCourseIdAndStatus(Long memberId, Long courseId, EnrollmentStatus status);

    /**
     * 강좌별 수강생 수를 한 번에 집계한다(취소=soft delete 제외).
     */
    @Query("""
            SELECT e.courseId AS courseId, COUNT(e) AS count
            FROM EnrollmentJpaEntity e
            WHERE e.courseId IN :courseIds
              AND e.deletedAt IS NULL
            GROUP BY e.courseId
            """)
    List<CourseEnrollmentCount> countByCourseIds(@Param("courseIds") List<Long> courseIds);

    interface CourseEnrollmentCount {
        Long getCourseId();

        long getCount();
    }

}
