package com.ohgiraffers.lxp.instructor.application.port.in;

import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorProfile;

public interface GetInstructorProfileUseCase {

    InstructorProfile get(Long instructorId);
}
