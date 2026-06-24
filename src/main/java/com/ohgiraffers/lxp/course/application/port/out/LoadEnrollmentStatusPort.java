package com.ohgiraffers.lxp.course.application.port.out;

/**
 * enrollment 컨텍스트에서 수강 인원 수와 수강 여부를 한 번에 조회한다(직접 의존 금지, 포트 경유).
 */
public interface LoadEnrollmentStatusPort {

    /**
     * @param memberId 로그인 사용자 ID. null이면 enrolled=false로 판정(수강 여부 조회 생략).
     */
    EnrollmentStatusView load(Long courseId, Long memberId);
}
