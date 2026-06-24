package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ohgiraffers.lxp.course.application.port.out.CourseListView;
import com.ohgiraffers.lxp.course.application.port.out.LoadCourseListPort;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;

@Repository
public class LoadCourseListPersistenceAdapter implements LoadCourseListPort {

    private final CourseJpaRepository courseJpaRepository;

    public LoadCourseListPersistenceAdapter(CourseJpaRepository courseJpaRepository) {
        this.courseJpaRepository = courseJpaRepository;
    }

    @Override
    public List<CourseListView> loadPublicCourses() {
        return courseJpaRepository.findByStatusAndDeletedAtIsNullOrderByCreatedAtDesc(CourseStatus.PUBLIC).stream()
                .map(entity -> new CourseListView(
                        entity.getId(),
                        entity.getTitle(),
                        entity.getThumbnailUrl(),
                        entity.getInstructorId()
                ))
                .toList();
    }
}
