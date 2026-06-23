package com.ohgiraffers.lxp.qna.application.port.out;

public interface EnrollmentQueryPort {

    boolean isEnrolled(Long courseId, Long memberId);
}
