package com.ohgiraffers.lxp.instructor.application.port.out;

import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorProfile;

import java.util.Optional;

public interface InstructorProfileRepositoryPort {

    InstructorProfile save(InstructorProfile profile);

    Optional<InstructorProfile> findByInstructorId(Long instructorId);
}
