package com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorApplicationRepositoryPort;
import com.ohgiraffers.lxp.instructor.domain.model.entity.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorApplication;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InstructorApplicationPersistenceAdapter implements InstructorApplicationRepositoryPort {

    private final InstructorApplicationJpaRepository jpaRepository;

    public InstructorApplicationPersistenceAdapter(InstructorApplicationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public InstructorApplication save(InstructorApplication application) {
        try {
            InstructorApplicationJpaEntity entity = InstructorApplicationJpaEntity.from(application);
            return jpaRepository.saveAndFlush(entity).toDomain();
        } catch (DataIntegrityViolationException e) {
            Throwable rootCause = e.getCause();
            if (rootCause instanceof ConstraintViolationException constraintEx) {
                String name = constraintEx.getConstraintName();
                if (name != null && name.contains("pending_lock")) {
                    throw new BusinessException(ErrorCode.INSTRUCTOR_APPLICATION_ALREADY_EXISTS);
                }
            }
            throw e;
        }
    }

    @Override
    public Optional<InstructorApplication> findById(Long id) {
        return jpaRepository.findById(id).map(InstructorApplicationJpaEntity::toDomain);
    }

    @Override
    public boolean existsByMemberIdAndStatus(Long memberId, ApplicationStatus status) {
        return jpaRepository.existsByMemberIdAndStatus(memberId, status);
    }
}
