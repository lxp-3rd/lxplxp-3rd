package com.ohgiraffers.lxp.enrollment.application.dto;

/**
 * LoadMemberInfoPort가 반환하는 회원 정보(수강신청 불변식 판정에 필요한 최소 표현).
 */
public record MemberInfo(Long memberId, Role role, boolean suspended) {

    public boolean isLearner() {
        return role == Role.LEARNER;
    }

    public boolean isSuspended() {
        return suspended;
    }
}
