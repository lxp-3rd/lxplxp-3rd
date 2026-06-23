package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.in.UpdateInstructorProfileUseCase;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorProfileRepository;
import com.ohgiraffers.lxp.instructor.domain.InstructorProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateInstructorProfileService implements UpdateInstructorProfileUseCase {

    private final InstructorProfileRepository instructorProfileRepository;

    public UpdateInstructorProfileService(InstructorProfileRepository instructorProfileRepository) {
        this.instructorProfileRepository = instructorProfileRepository;
    }

    @Override
    public void update(UpdateInstructorProfileCommand command) {
        InstructorProfile profile = instructorProfileRepository
                .findByInstructorId(command.instructorId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INSTRUCTOR_PROFILE_NOT_FOUND));

        profile.update(command.profileImageUrl(), command.bio());
        instructorProfileRepository.save(profile);
    }
}
