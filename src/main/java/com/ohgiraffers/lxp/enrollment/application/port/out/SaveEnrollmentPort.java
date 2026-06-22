package com.ohgiraffers.lxp.enrollment.application.port.out;

import com.ohgiraffers.lxp.enrollment.application.dto.EnrollmentResult;
import com.ohgiraffers.lxp.enrollment.domain.model.entity.Enrollment;

public interface SaveEnrollmentPort {

    EnrollmentResult save(Enrollment enrollment);
}
