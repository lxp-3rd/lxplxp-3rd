package com.ohgiraffers.lxp.instructor.application.port.in;

import com.ohgiraffers.lxp.instructor.application.dto.InstructorApplicationResult;

import java.util.List;

public interface GetInstructorApplicationListUseCase {

    List<InstructorApplicationResult> getApplications();
}
