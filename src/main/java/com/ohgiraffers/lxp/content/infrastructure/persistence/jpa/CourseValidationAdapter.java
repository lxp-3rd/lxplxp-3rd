package com.ohgiraffers.lxp.content.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.content.application.port.out.CourseValidationPort;
import com.ohgiraffers.lxp.course.infrastructure.persistence.jpa.CourseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CourseValidationAdapter implements CourseValidationPort {

    private final CourseJpaRepository courseJpaRepository;

    public CourseValidationAdapter(CourseJpaRepository courseJpaRepository) {
        this.courseJpaRepository = courseJpaRepository;
    }

    @Override
    public boolean existsById(Long courseId) {
        return courseJpaRepository.existsByIdAndDeletedAtIsNull(courseId);
    }
}
