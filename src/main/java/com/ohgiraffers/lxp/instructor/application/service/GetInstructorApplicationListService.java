package com.ohgiraffers.lxp.instructor.application.service;

import com.ohgiraffers.lxp.instructor.application.dto.InstructorApplicationResult;
import com.ohgiraffers.lxp.instructor.application.port.in.GetInstructorApplicationListUseCase;
import com.ohgiraffers.lxp.instructor.application.port.out.InstructorApplicationRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetInstructorApplicationListService implements GetInstructorApplicationListUseCase {

    private final InstructorApplicationRepositoryPort instructorApplicationRepository;

    public GetInstructorApplicationListService(InstructorApplicationRepositoryPort instructorApplicationRepository) {
        this.instructorApplicationRepository = instructorApplicationRepository;
    }

    @Override
    public List<InstructorApplicationResult> getApplications() {
        return instructorApplicationRepository.findAll().stream()
                .map(InstructorApplicationResult::from)
                .toList();
    }
}
