package com.ohgiraffers.lxp.instructor.application.port.in;

import com.ohgiraffers.lxp.instructor.domain.model.entity.Instructor;

public interface GetMyInstructorUseCase {

    Instructor getByMemberId(Long memberId);
}
