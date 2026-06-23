package com.ohgiraffers.lxp.enrollment.application.port.in;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.application.port.command.CancelEnrollmentCommand;

public interface CancelEnrollmentUseCase {

    EnrollmentResult cancel(CancelEnrollmentCommand command);

}
