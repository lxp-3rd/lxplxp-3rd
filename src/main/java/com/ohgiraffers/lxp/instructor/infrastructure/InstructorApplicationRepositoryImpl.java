package com.ohgiraffers.lxp.instructor.infrastructure;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorApplicationRepository;
import com.ohgiraffers.lxp.instructor.domain.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.InstructorApplication;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class InstructorApplicationRepositoryImpl implements InstructorApplicationRepository {

    private final InstructorApplicationJpaRepository jpaRepository;

    public InstructorApplicationRepositoryImpl(InstructorApplicationJpaRepository jpaRepository) {
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
    public boolean existsByMemberIdAndStatus(Long memberId, ApplicationStatus status) {
        return jpaRepository.existsByMemberIdAndStatus(memberId, status);
    }
}
