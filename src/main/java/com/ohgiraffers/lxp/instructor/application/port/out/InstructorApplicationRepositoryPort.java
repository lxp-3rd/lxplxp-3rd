package com.ohgiraffers.lxp.instructor.application.port.out;

import com.ohgiraffers.lxp.instructor.domain.model.entity.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorApplication;

import java.util.Optional;

public interface InstructorApplicationRepositoryPort {

    InstructorApplication save(InstructorApplication application);

    Optional<InstructorApplication> findById(Long id);

    boolean existsByMemberIdAndStatus(Long memberId, ApplicationStatus status);
}
