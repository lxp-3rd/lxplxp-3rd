package com.ohgiraffers.lxp.instructor.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorProfileJpaRepository extends JpaRepository<InstructorProfileJpaEntity, Long> {

    Optional<InstructorProfileJpaEntity> findByInstructorId(Long instructorId);
}
