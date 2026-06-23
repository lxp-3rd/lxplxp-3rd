package com.ohgiraffers.lxp.enrollment.application.port.out;

public interface LoadEnrollmentPort {

    boolean existsActiveEnrollment(Long memberId, Long courseId);

}
