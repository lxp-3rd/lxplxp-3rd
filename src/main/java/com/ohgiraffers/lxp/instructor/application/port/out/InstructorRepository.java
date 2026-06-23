package com.ohgiraffers.lxp.instructor.application.port.out;

import com.ohgiraffers.lxp.instructor.domain.Instructor;
import com.ohgiraffers.lxp.instructor.domain.InstructorStatus;

import java.util.List;

public interface InstructorRepository {

    Instructor save(Instructor instructor);

    boolean existsById(Long id);

    boolean existsByMemberIdAndStatusIn(Long memberId, List<InstructorStatus> statuses);
}
