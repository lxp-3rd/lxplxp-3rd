package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.in.RegisterInstructorProfileUseCase;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorProfileRepository;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorRepository;
import com.ohgiraffers.lxp.instructor.domain.InstructorProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterInstructorProfileService implements RegisterInstructorProfileUseCase {

    private final InstructorRepository instructorRepository;
    private final InstructorProfileRepository instructorProfileRepository;

    public RegisterInstructorProfileService(
            InstructorRepository instructorRepository,
            InstructorProfileRepository instructorProfileRepository
    ) {
        this.instructorRepository = instructorRepository;
        this.instructorProfileRepository = instructorProfileRepository;
    }

    @Override
    public void register(RegisterInstructorProfileCommand command) {
        if (!instructorRepository.existsById(command.instructorId())) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_NOT_FOUND);
        }

        if (instructorProfileRepository.findByInstructorId(command.instructorId()).isPresent()) {
            throw new BusinessException(ErrorCode.INSTRUCTOR_PROFILE_ALREADY_EXISTS);
        }

        instructorProfileRepository.save(
                InstructorProfile.create(command.instructorId(), command.profileImageUrl(), command.bio())
        );
    }
}
