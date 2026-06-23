package com.ohgiraffers.lxp.instructor.application.port.out;

import com.ohgiraffers.lxp.instructor.domain.InstructorProfile;

import java.util.Optional;

public interface InstructorProfileRepository {

    InstructorProfile save(InstructorProfile profile);

    Optional<InstructorProfile> findByInstructorId(Long instructorId);
}
