package com.ohgiraffers.lxp.course.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.course.application.port.out.InstructorValidationPort;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorStatus;
import com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa.InstructorJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstructorValidationAdapter implements InstructorValidationPort {

    private final InstructorJpaRepository instructorJpaRepository;

    public InstructorValidationAdapter(InstructorJpaRepository instructorJpaRepository) {
        this.instructorJpaRepository = instructorJpaRepository;
    }

    @Override
    public boolean isActiveInstructor(Long instructorId) {
        return instructorJpaRepository.existsByIdAndStatusInAndDeletedAtIsNull(
                instructorId,
                List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED)
        );
    }
}
