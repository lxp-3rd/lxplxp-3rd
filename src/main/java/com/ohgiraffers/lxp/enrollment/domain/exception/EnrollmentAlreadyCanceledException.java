package com.ohgiraffers.lxp.enrollment.domain.exception;

import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;

public class EnrollmentAlreadyCanceledException extends BusinessException {

    public EnrollmentAlreadyCanceledException() {
        super(ErrorCode.ENROLLMENT_ALREADY_CANCELED);
    }
}
