package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ohgiraffers.lxp.content.infrastructure.persistence.jpa.ContentJpaEntity;
import com.ohgiraffers.lxp.content.infrastructure.persistence.jpa.ContentJpaRepository;
import com.ohgiraffers.lxp.course.application.port.out.CourseDetailView;
import com.ohgiraffers.lxp.course.application.port.out.LoadCourseDetailPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.read.CurriculumItem;

@Repository
public class LoadCourseDetailPersistenceAdapter implements LoadCourseDetailPort {

    private final CourseJpaRepository courseJpaRepository;
    private final ContentJpaRepository contentJpaRepository;

    public LoadCourseDetailPersistenceAdapter(CourseJpaRepository courseJpaRepository,
                                              ContentJpaRepository contentJpaRepository) {
        this.courseJpaRepository = courseJpaRepository;
        this.contentJpaRepository = contentJpaRepository;
    }

    @Override
    public Optional<CourseDetailView> loadPublicCourseDetail(Long courseId) {
        return courseJpaRepository.findByIdAndStatusAndDeletedAtIsNull(courseId, CourseStatus.PUBLIC)
                .map(CourseJpaEntity::toDomain)
                .map(course -> new CourseDetailView(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription(),
                        course.getThumbnailUrl(),
                        loadCurriculum(courseId)
                ));
    }

    private List<CurriculumItem> loadCurriculum(Long courseId) {
        return contentJpaRepository.findByCourseIdAndDeletedAtIsNullOrderBySequenceAsc(courseId).stream()
                .map(ContentJpaEntity::toDomain)
                .map(content -> new CurriculumItem(content.getId(), content.getSequence(), content.getTitle()))
                .toList();
    }
}
