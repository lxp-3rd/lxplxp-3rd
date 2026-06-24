package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import com.ohgiraffers.lxp.instructor.application.port.in.GetMyInstructorUseCase;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorRepositoryPort;
import com.ohgiraffers.lxp.instructor.domain.model.entity.Instructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetMyInstructorService implements GetMyInstructorUseCase {

    private final InstructorRepositoryPort instructorRepository;

    public GetMyInstructorService(InstructorRepositoryPort instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Instructor getByMemberId(Long memberId) {
        return instructorRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INSTRUCTOR_NOT_FOUND));
    }
}
