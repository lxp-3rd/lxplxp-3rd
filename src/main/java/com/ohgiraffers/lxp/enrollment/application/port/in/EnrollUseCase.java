package com.ohgiraffers.lxp.enrollment.application.port.in;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.application.port.command.EnrollCommand;

public interface EnrollUseCase {

    EnrollmentResult enroll(EnrollCommand command);

}
