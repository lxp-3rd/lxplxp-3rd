package com.ohgiraffers.lxp.enrollment.application.exception;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

/**
 * 정지된 회원이 수강신청할 때 발생. ErrorCode 매핑: MEMBER_SUSPENDED (403 FORBIDDEN)
 */
public class MemberSuspendedException extends BusinessException {

    public MemberSuspendedException() {
        super(ErrorCode.MEMBER_SUSPENDED);
    }

}
