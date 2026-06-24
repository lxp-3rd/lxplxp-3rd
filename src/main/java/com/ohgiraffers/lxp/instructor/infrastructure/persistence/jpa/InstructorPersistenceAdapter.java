package com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.instructor.application.port.out.InstructorRepositoryPort;
import com.ohgiraffers.lxp.instructor.domain.model.entity.Instructor;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InstructorPersistenceAdapter implements InstructorRepositoryPort {

    private final InstructorJpaRepository jpaRepository;

    public InstructorPersistenceAdapter(InstructorJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Instructor save(Instructor instructor) {
        return jpaRepository.save(InstructorJpaEntity.from(instructor)).toDomain();
    }

    @Override
    public Optional<Instructor> findByMemberId(Long memberId) {
        return jpaRepository.findByMemberId(memberId).map(InstructorJpaEntity::toDomain);
    }

    @Override
    public boolean existsByIdAndStatusIn(Long id, List<InstructorStatus> statuses) {
        return jpaRepository.existsByIdAndStatusInAndDeletedAtIsNull(id, statuses);
    }

    @Override
    public boolean existsByMemberIdAndStatusIn(Long memberId, List<InstructorStatus> statuses) {
        return jpaRepository.existsByMemberIdAndStatusIn(memberId, statuses);
    }
}
