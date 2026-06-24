package com.ohgiraffers.lxp.content.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentJpaRepository extends JpaRepository<ContentJpaEntity, Long> {
    Optional<ContentJpaEntity> findByIdAndDeletedAtIsNull(Long id);
    List<ContentJpaEntity> findAllByDeletedAtIsNull();
    long countByDeletedAtIsNull();
    List<ContentJpaEntity> findByCourseIdAndDeletedAtIsNullOrderBySequenceAsc(Long courseId);
}
