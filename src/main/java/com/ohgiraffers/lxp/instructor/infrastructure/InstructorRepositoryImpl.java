package com.ohgiraffers.lxp.instructor.infrastructure;

import com.ohgiraffers.lxp.instructor.application.port.out.InstructorRepository;
import com.ohgiraffers.lxp.instructor.domain.Instructor;
import com.ohgiraffers.lxp.instructor.domain.InstructorStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstructorRepositoryImpl implements InstructorRepository {

    private final InstructorJpaRepository jpaRepository;

    public InstructorRepositoryImpl(InstructorJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Instructor save(Instructor instructor) {
        return jpaRepository.save(InstructorJpaEntity.from(instructor)).toDomain();
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existsByMemberIdAndStatusIn(Long memberId, List<InstructorStatus> statuses) {
        return jpaRepository.existsByMemberIdAndStatusIn(memberId, statuses);
    }
}
