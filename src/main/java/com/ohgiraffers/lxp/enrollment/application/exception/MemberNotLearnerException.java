package com.ohgiraffers.lxp.enrollment.application.exception;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

/**
 * 수강생(LEARNER) 역할이 아닌 회원이 수강신청할 때 발생.
 * ErrorCode 매핑: MEMBER_NOT_LEARNER (403 FORBIDDEN)
 */
public class MemberNotLearnerException extends BusinessException {

    public MemberNotLearnerException() {
        super(ErrorCode.MEMBER_NOT_LEARNER);
    }
}
