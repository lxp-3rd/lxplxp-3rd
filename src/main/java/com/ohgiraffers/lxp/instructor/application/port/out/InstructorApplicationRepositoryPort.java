package com.ohgiraffers.lxp.instructor.application.port.out;

import com.ohgiraffers.lxp.instructor.domain.model.entity.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorApplication;

import java.util.List;
import java.util.Optional;

public interface InstructorApplicationRepositoryPort {

    InstructorApplication save(InstructorApplication application);

    Optional<InstructorApplication> findById(Long id);

    List<InstructorApplication> findAll();

    boolean existsByMemberIdAndStatus(Long memberId, ApplicationStatus status);
}
