package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.in.GetInstructorProfileUseCase;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorProfileRepository;
import com.ohgiraffers.lxp.instructor.domain.InstructorProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetInstructorProfileService implements GetInstructorProfileUseCase {

    private final InstructorProfileRepository instructorProfileRepository;

    public GetInstructorProfileService(InstructorProfileRepository instructorProfileRepository) {
        this.instructorProfileRepository = instructorProfileRepository;
    }

    @Override
    public InstructorProfile get(Long instructorId) {
        return instructorProfileRepository.findByInstructorId(instructorId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INSTRUCTOR_PROFILE_NOT_FOUND));
    }
}
