package com.ohgiraffers.lxp.instructor.infrastructure;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorProfileRepository;
import com.ohgiraffers.lxp.instructor.domain.InstructorProfile;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return jpaRepository.saveAndFlush(InstructorProfileJpaEntity.from(profile)).toDomain();
        } catch (DataIntegrityViolationException e) {
            Throwable rootCause = e.getCause();
            if (rootCause instanceof ConstraintViolationException constraintEx) {
                String name = constraintEx.getConstraintName();
                if (name != null && name.contains("instructor_id")) {
                    throw new BusinessException(ErrorCode.INSTRUCTOR_PROFILE_ALREADY_EXISTS);
                }
            }
            throw e;
        }
    }

    @Override
    public Optional<InstructorProfile> findByInstructorId(Long instructorId) {
        return jpaRepository.findByInstructorId(instructorId)
                .map(InstructorProfileJpaEntity::toDomain);
    }
}
