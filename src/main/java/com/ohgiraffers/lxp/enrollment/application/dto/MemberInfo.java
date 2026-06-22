package com.ohgiraffers.lxp.enrollment.application.dto;

public record MemberInfo(Long memberId, Role role, boolean suspended) {

    public boolean isLearner() {
        return role == Role.LEARNER;
    }

    public boolean isSuspended() {
        return suspended;
    }
    
}
