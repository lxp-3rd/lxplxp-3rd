package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.course.application.port.out.CourseRepositoryPort;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CoursePersistenceAdapter implements CourseRepositoryPort {

    private final CourseJpaRepository jpaRepository;

    public CoursePersistenceAdapter(CourseJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Course save(Course course) {
        return jpaRepository.save(CourseJpaEntity.from(course)).toDomain();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return jpaRepository.findByIdAndDeletedAtIsNull(id)
                .map(CourseJpaEntity::toDomain);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.findByIdAndDeletedAtIsNull(id)
                .ifPresent(entity -> {
                    entity.delete();
                    jpaRepository.save(entity);
                });
    }
}
