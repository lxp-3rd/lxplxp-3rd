package com.ohgiraffers.lxp.enrollment.application.exception;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

/**
 * 수강생(LEARNER) 역할이 아닌 회원이 수강신청할 때 발생
 */
public class MemberNotLearnerException extends BusinessException {

    public MemberNotLearnerException() {
        super(ErrorCode.MEMBER_NOT_STUDENT);
    }
}
