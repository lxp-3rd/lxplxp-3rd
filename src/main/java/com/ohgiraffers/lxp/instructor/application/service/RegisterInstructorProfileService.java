package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.command.RegisterInstructorProfileCommand;
import com.ohgiraffers.lxp.instructor.application.port.in.RegisterInstructorProfileUseCase;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorProfileRepositoryPort;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorRepositoryPort;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorProfile;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorStatus;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterInstructorProfileService implements RegisterInstructorProfileUseCase {

    private final InstructorRepositoryPort instructorRepository;
    private final InstructorProfileRepositoryPort instructorProfileRepository;

    public RegisterInstructorProfileService(
            InstructorRepositoryPort instructorRepository,
            InstructorProfileRepositoryPort instructorProfileRepository
    ) {
        this.instructorRepository = instructorRepository;
        this.instructorProfileRepository = instructorProfileRepository;
    }

    @Override
    public void register(RegisterInstructorProfileCommand command) {
        if (!instructorRepository.existsByIdAndStatusIn(
                command.instructorId(), List.of(InstructorStatus.ACTIVE, InstructorStatus.SUSPENDED))) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_NOT_FOUND);
        }

        if (instructorProfileRepository.findByInstructorId(command.instructorId()).isPresent()) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_PROFILE_ALREADY_EXISTS);
        }

        try {
            instructorProfileRepository.save(
                    InstructorProfile.create(command.instructorId(), command.profileImageUrl(), command.bio())
            );
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
    }
}
