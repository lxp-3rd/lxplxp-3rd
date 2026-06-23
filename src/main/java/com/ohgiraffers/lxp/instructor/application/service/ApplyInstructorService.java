package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.command.ApplyInstructorCommand;
import com.ohgiraffers.lxp.instructor.application.port.in.ApplyInstructorUseCase;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorApplicationRepositoryPort;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorRepositoryPort;
import com.ohgiraffers.lxp.instructor.domain.model.entity.ApplicationStatus;
import com.ohgiraffers.lxp.instructor.domain.model.entity.Instructor;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorApplication;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplyInstructorService implements ApplyInstructorUseCase {

    private final InstructorApplicationRepositoryPort instructorApplicationRepository;
    private final InstructorRepositoryPort instructorRepository;

    public ApplyInstructorService(
            InstructorApplicationRepositoryPort instructorApplicationRepository,
            InstructorRepositoryPort instructorRepository
    ) {
        this.instructorApplicationRepository = instructorApplicationRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public void apply(ApplyInstructorCommand command) {
        if (instructorApplicationRepository.existsByMemberIdAndStatus(
                command.memberId(), ApplicationStatus.PENDING)) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_APPLICATION_ALREADY_EXISTS);
        }

        if (instructorRepository.existsByMemberIdAndStatusIn(
                command.memberId(), List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED))) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_ALREADY_EXISTS);
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
