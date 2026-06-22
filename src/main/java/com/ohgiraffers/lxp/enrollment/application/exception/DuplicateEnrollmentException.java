package com.ohgiraffers.lxp.enrollment.application.exception;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

/**
 * 이미 ACTIVE 수강이 존재하는 회원·강좌에 다시 신청할 때 발생
 * ErrorCode 매핑: ENROLLMENT_ALREADY_EXISTS (409 CONFLICT)
 */
public class DuplicateEnrollmentException extends BusinessException {

    public DuplicateEnrollmentException() {
        super(ErrorCode.ENROLLMENT_ALREADY_EXISTS);
    }
}
