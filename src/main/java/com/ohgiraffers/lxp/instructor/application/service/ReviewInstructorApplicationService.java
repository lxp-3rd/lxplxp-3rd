package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.command.ReviewAction;
import com.ohgiraffers.lxp.instructor.application.port.command.ReviewInstructorApplicationCommand;
import com.ohgiraffers.lxp.instructor.application.port.in.ReviewInstructorApplicationUseCase;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorApplicationRepositoryPort;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorRepositoryPort;
import com.ohgiraffers.lxp.instructor.domain.model.entity.Instructor;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ReviewInstructorApplicationService implements ReviewInstructorApplicationUseCase {

    private final InstructorApplicationRepositoryPort instructorApplicationRepository;
    private final InstructorRepositoryPort instructorRepository;

    public ReviewInstructorApplicationService(
            InstructorApplicationRepositoryPort instructorApplicationRepository,
            InstructorRepositoryPort instructorRepository
    ) {
        this.instructorApplicationRepository = instructorApplicationRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public void review(ReviewInstructorApplicationCommand command) {
        InstructorApplication application = instructorApplicationRepository
                .findById(command.applicationId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INSTRUCTOR_APPLICATION_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();

        try {
            if (command.action() == ReviewAction.APPROVE) {
                application.approve(now);
                instructorApplicationRepository.save(application);
                instructorRepository.save(Instructor.create(application.getMemberId()));
            } else {
                application.reject(command.rejectionReason(), now);
                instructorApplicationRepository.save(application);
            }
        } catch (IllegalStateException e) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_APPLICATION_ALREADY_REVIEWED);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_APPLICATION_REJECTION_REASON_REQUIRED);
        }
    }
}
