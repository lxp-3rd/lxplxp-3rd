package com.ohgiraffers.lxp.enrollment.application.port.command;

/**
 * 수강신청 유스케이스 입력값. memberId는 현재 요청 body에서 받는다. TODO: auth 구현 후 SecurityContext의 인증 주체로 교체.
 */
public record EnrollCommand(Long memberId, Long courseId) {

}
