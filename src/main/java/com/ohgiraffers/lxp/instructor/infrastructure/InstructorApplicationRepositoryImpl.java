package com.ohgiraffers.lxp.instructor.infrastructure;

import com.ohgiraffers.lxp.instructor.application.port.out.InstructorApplicationRepository;
import com.ohgiraffers.lxp.instructor.domain.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.InstructorApplication;
import org.springframework.stereotype.Repository;

@Repository
public class InstructorApplicationRepositoryImpl implements InstructorApplicationRepository {

    private final InstructorApplicationJpaRepository jpaRepository;

    public InstructorApplicationRepositoryImpl(InstructorApplicationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public InstructorApplication save(InstructorApplication application) {
        InstructorApplicationJpaEntity entity = InstructorApplicationJpaEntity.from(application);
        return jpaRepository.save(entity).toDomain();
    }

    @Override
    public boolean existsByMemberIdAndStatus(Long memberId, ApplicationStatus status) {
        return jpaRepository.existsByMemberIdAndStatus(memberId, status);
    }
}
