package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseJpaRepository extends JpaRepository<CourseJpaEntity, Long> {

    Optional<CourseJpaEntity> findByIdAndDeletedAtIsNull(Long id);

    List<CourseJpaEntity> findAllByDeletedAtIsNull();

    boolean existsByIdAndDeletedAtIsNull(Long id);
}
