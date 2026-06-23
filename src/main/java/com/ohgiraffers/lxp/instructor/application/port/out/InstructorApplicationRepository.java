package com.ohgiraffers.lxp.instructor.application.port.out;

import com.ohgiraffers.lxp.instructor.domain.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.InstructorApplication;

public interface InstructorApplicationRepository {

    InstructorApplication save(InstructorApplication application);

    boolean existsByMemberIdAndStatus(Long memberId, ApplicationStatus status);
}
