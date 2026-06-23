package com.ohgiraffers.lxp.instructor.infrastructure;

import com.ohgiraffers.lxp.instructor.application.port.out.InstructorProfileRepository;
import com.ohgiraffers.lxp.instructor.domain.InstructorProfile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InstructorProfileRepositoryImpl implements InstructorProfileRepository {

    private final InstructorProfileJpaRepository jpaRepository;

    public InstructorProfileRepositoryImpl(InstructorProfileJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public InstructorProfile save(InstructorProfile profile) {
        return jpaRepository.save(InstructorProfileJpaEntity.from(profile)).toDomain();
    }

    @Override
    public Optional<InstructorProfile> findByInstructorId(Long instructorId) {
        return jpaRepository.findByInstructorId(instructorId)
                .map(InstructorProfileJpaEntity::toDomain);
    }
}
