package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.in.ReviewAction;
import com.ohgiraffers.lxp.instructor.application.port.in.ReviewInstructorApplicationCommand;
import com.ohgiraffers.lxp.instructor.application.port.in.ReviewInstructorApplicationUseCase;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorApplicationRepository;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorRepository;
import com.ohgiraffers.lxp.instructor.domain.Instructor;
import com.ohgiraffers.lxp.instructor.domain.InstructorApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ReviewInstructorApplicationService implements ReviewInstructorApplicationUseCase {

    private final InstructorApplicationRepository instructorApplicationRepository;
    private final InstructorRepository instructorRepository;

    public ReviewInstructorApplicationService(
            InstructorApplicationRepository instructorApplicationRepository,
            InstructorRepository instructorRepository
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
