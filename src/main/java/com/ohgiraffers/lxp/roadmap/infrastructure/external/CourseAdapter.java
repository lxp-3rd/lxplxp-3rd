package com.ohgiraffers.lxp.roadmap.infrastructure.external;

import com.ohgiraffers.lxp.course.infrastructure.persistence.jpa.CourseJpaRepository;
import com.ohgiraffers.lxp.roadmap.application.port.out.CoursePort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseAdapter implements CoursePort {

    private final CourseJpaRepository courseJpaRepository;

    public CourseAdapter(CourseJpaRepository courseJpaRepository) {
        this.courseJpaRepository = courseJpaRepository;
    }

    @Override
    public boolean existsAllByIds(List<Long> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            return false;
        }
        return courseIds.stream().allMatch(courseJpaRepository::existsByIdAndDeletedAtIsNull);
    }
}
