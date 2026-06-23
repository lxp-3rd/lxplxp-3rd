package com.ohgiraffers.lxp.enrollment.application.dto;

/**
 * 회원 역할(수강 컨텍스트가 필요로 하는 최소 표현).
 * TODO: member 도메인 구현 후 그쪽 역할 정의와 정렬한다.
 */
public enum Role {
    LEARNER,
    INSTRUCTOR,
    ADMIN
}
