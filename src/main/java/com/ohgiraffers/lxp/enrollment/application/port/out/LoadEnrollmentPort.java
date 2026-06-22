package com.ohgiraffers.lxp.enrollment.application.port.out;

/**
 * 수강 조회 아웃바운드 포트.
 */
public interface LoadEnrollmentPort {

    boolean existsActiveEnrollment(Long memberId, Long courseId);

}
