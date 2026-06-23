package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.in.ApplyInstructorUseCase;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorApplicationRepository;
import com.ohgiraffers.lxp.instructor.domain.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.InstructorApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplyInstructorService implements ApplyInstructorUseCase {

    private final InstructorApplicationRepository instructorApplicationRepository;

    public ApplyInstructorService(InstructorApplicationRepository instructorApplicationRepository) {
        this.instructorApplicationRepository = instructorApplicationRepository;
    }

    @Override
    public void apply(ApplyInstructorCommand command) {
        if (instructorApplicationRepository.existsByMemberIdAndStatus(
                command.memberId(), ApplicationStatus.PENDING)) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_APPLICATION_ALREADY_EXISTS);
        }

        InstructorApplication application = InstructorApplication.apply(
                command.memberId(),
                command.instructorName(),
                command.introduction(),
                command.expertise()
        );

        instructorApplicationRepository.save(application);
    }
}
