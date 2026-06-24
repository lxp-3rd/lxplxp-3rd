package com.ohgiraffers.lxp.enrollment.application.port.in;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.application.port.command.CompleteEnrollmentCommand;

public interface CompleteEnrollmentUseCase {

    EnrollmentResult complete(CompleteEnrollmentCommand command);
}
