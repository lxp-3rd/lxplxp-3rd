package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ohgiraffers.lxp.course.application.port.out.LoadCourseLikeCountPort;
import com.ohgiraffers.lxp.course.infrastructure.persistence.jpa.CourseLikeJpaRepository.CourseLikeCount;

@Repository
public class CourseLikeCountAdapter implements LoadCourseLikeCountPort {

    private final CourseLikeJpaRepository courseLikeJpaRepository;

    public CourseLikeCountAdapter(CourseLikeJpaRepository courseLikeJpaRepository) {
        this.courseLikeJpaRepository = courseLikeJpaRepository;
    }

    @Override
    public Map<Long, Long> countByCourseIds(List<Long> courseIds) {
        if (courseIds.isEmpty()) {
            return Map.of();
        }
        return courseLikeJpaRepository.countByCourseIds(courseIds).stream()
                .collect(Collectors.toMap(
                        CourseLikeCount::getCourseId,
                        CourseLikeCount::getCount
                ));
    }
}
