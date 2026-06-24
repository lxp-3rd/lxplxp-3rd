package com.ohgiraffers.lxp.instructor.application.port.out;

import com.ohgiraffers.lxp.instructor.domain.model.entity.Instructor;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorStatus;

import java.util.List;
import java.util.Optional;

public interface InstructorRepositoryPort {

    Instructor save(Instructor instructor);

    Optional<Instructor> findByMemberId(Long memberId);

    boolean existsByIdAndStatusIn(Long id, List<InstructorStatus> statuses);

    boolean existsByMemberIdAndStatusIn(Long memberId, List<InstructorStatus> statuses);
}
