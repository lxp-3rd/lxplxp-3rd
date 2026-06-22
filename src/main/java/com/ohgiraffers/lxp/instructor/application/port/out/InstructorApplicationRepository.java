package com.ohgiraffers.lxp.instructor.application.port.out;

import com.ohgiraffers.lxp.instructor.domain.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.InstructorApplication;

import java.util.Optional;

public interface InstructorApplicationRepository {

    InstructorApplication save(InstructorApplication application);

    Optional<InstructorApplication> findById(Long id);

    boolean existsByMemberIdAndStatus(Long memberId, ApplicationStatus status);
}
