package com.ohgiraffers.lxp.course.application.port.out;

/**
 * 특정 강좌에 대한 수강 현황(현재 수강 중 인원 수 + 요청 사용자의 수강 여부).
 */
public record EnrollmentStatusView(
        long enrollmentCount,
        boolean enrolled
) {}
